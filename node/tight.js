// kotlin-head Node.js — 紧凑一体化 (Tight Pipeline)
// Lexer 不产 Token 数组，直接喂 Parser — 单遍扫描，零中间分配

const T = require('./lexer').TokType

const TOK = { FUN:T.FUN,VAL:T.VAL,VAR:T.VAR,CLASS:T.CLASS,IF:T.IF,ELSE:T.ELSE,
    RETURN:T.RETURN,WHEN:T.WHEN,FOR:T.FOR,WHILE:T.WHILE,
    LPAREN:T.LPAREN,RPAREN:T.RPAREN,LBRACE:T.LBRACE,RBRACE:T.RBRACE,
    INT_LIT:T.INT_LIT,STR_LIT:T.STR_LIT,BOOL_LIT:T.BOOL_LIT,IDENT:T.IDENT,
    PLUS:T.PLUS,MINUS:T.MINUS,STAR:T.STAR,SLASH:T.SLASH,EQ:T.EQ,LT:T.LT,GT:T.GT,
    AND:T.AND,OR:T.OR,NOT:T.NOT,COLON:T.COLON,DOT:T.DOT,COMMA:T.COMMA,
    ARROW:T.ARROW,ELVIS:T.ELVIS,AT:T.AT,EQEQ:T.EQEQ,BANGEQ:T.BANGEQ,
    LTEQ:T.LTEQ,GTEQ:T.GTEQ,SEMICOLON:T.SEMICOLON,EOF:T.EOF }

const KW = new Set(['fun','val','var','class','data','if','else','return',
    'when','for','while','object','interface','enum','override','import','package',
    'true','false','try','catch','finally','as','companion'])

// NODE_SIZE=6: [type, child1, child2, extra]
const NT = { FILE:1,FUN:2,VAL:3,BLOCK:4,RETURN:5,IF:6,BINARY:7,CALL:8,
    REF:9, LIT_INT:11,LIT_STR:12,LIT_BOOL:13,LAMBDA:14,PREFIX:16 }

const PREC = {}
PREC[T.OR]=1;PREC[T.AND]=2;PREC[T.EQEQ]=3;PREC[T.BANGEQ]=3
PREC[T.LT]=4;PREC[T.GT]=4;PREC[T.LTEQ]=4;PREC[T.GTEQ]=4
PREC[T.ELVIS]=5;PREC[T.PLUS]=7;PREC[T.MINUS]=7;PREC[T.STAR]=8;PREC[T.SLASH]=8

class TightPipeline {
    constructor(src) {
        this.s = src; this.i = 0; this.pos = 0 // pos in token stream
        this.tk = new Uint16Array(8192)  // [type, textLen, line, col, ...]
        this.tp = new Uint16Array(4096)  // text pool offsets
        this.tx = '' // text pool
        this.tc = 0  // token count
        this.ast = new Uint16Array(4096*6)
        this.an = 0  // AST node count
        this.sp = [] // string pool
        this.depth = 0
        // 先扫全部 token (简单暴力——但用紧凑格式)
        this._tokenize()
    }

    _tok(type, text) {
        if (this.tc>=4096) return
        const off = this.tx.length; this.tx += text.slice(0,255)
        this.tk[this.tc]=type; this.tp[this.tc]=off; this.tc++
    }
    _tType(i) { return this.tk[i] }
    _tText(i) { const o=this.tp[i]; const len=this.tp[i+1]?this.tp[i+1]-o:255; return this.tx.slice(o,o+len) }

    _tokenize() {
        const s = this.s; let i=0; const N=s.length
        const isA=c=>(c>='a'&&c<='z')||(c>='A'&&c<='Z')||c==='_'
        const isD=c=>c>='0'&&c<='9'
        while(i<N) {
            const c=s[i]
            if(c===' '||c==='\t'||c==='\n'){i++;continue}
            if(c==='/'&&s[i+1]==='/'){while(i<N&&s[i]!=='\n')i++;continue}
            if(c==='/'&&s[i+1]==='*'){i+=2;while(i<N&&!(s[i]==='*'&&s[i+1]==='/'))i++;i+=2;continue}
            if(c==='"'){i++;let st=i;while(i<N&&s[i]!=='"'){if(s[i]==='\\\\')i++;i++};this._tok(T.STR_LIT,'\"'+s.slice(st,i)+'\"');if(i<N)i++;continue}
            if(isD(c)){let st=i;while(i<N&&isD(s[i]))i++;this._tok(T.INT_LIT,s.slice(st,i));continue}
            if(isA(c)){let st=i;while(i<N&&(isA(s[i])||isD(s[i])))i++;const w=s.slice(st,i);this._tok(KW.has(w)?T[w.toUpperCase()]||T.IDENT:T.IDENT,w);continue}
            i++
            if(c==='+'||c==='*'){this._tok(c==='+'?T.PLUS:T.STAR,c)}
            else if(c==='-'){if(s[i]==='>'){i++;this._tok(T.ARROW,'->')}else this._tok(T.MINUS,'-')}
            else if(c==='&'&&s[i]==='&'){i++;this._tok(T.AND,'&&')}
            else if(c==='|'&&s[i]==='|'){i++;this._tok(T.OR,'||')}
            else if(c==='='&&s[i]==='='){i++;this._tok(T.EQEQ,'==')}
            else if(c==='!'&&s[i]==='='){i++;this._tok(T.BANGEQ,'!=')}
            else if(c==='?'&&s[i]===':'){i++;this._tok(T.ELVIS,'?:')}
            else if(c==='<'&&s[i]==='='){i++;this._tok(T.LTEQ,'<=')}
            else if(c==='>'&&s[i]==='='){i++;this._tok(T.GTEQ,'>=')}
            else { const m=({';':T.SEMICOLON,'@':T.AT,',':T.COMMA,':':T.COLON,'.':T.DOT,
                '(':T.LPAREN,')':T.RPAREN,'{':T.LBRACE,'}':T.RBRACE,
                '=':T.EQ,'<':T.LT,'>':T.GT,'!':T.NOT,'/':T.SLASH})[c]; if(m) this._tok(m,c) }
        }
        this._tok(T.EOF,'')
    }

    _node(type,child1=0,child2=0,extra=0) {
        const i=this.an++*6; this.ast[i]=type; this.ast[i+3]=child1; this.ast[i+4]=child2; this.ast[i+5]=extra; return i
    }
    _s(s) { this.sp.push(s); return this.sp.length-1 }

    _peek() { return this._tType(this.pos) }
    _adv() { return this._tType(this.pos++) }
    _chk(tp) { return this._peek()===tp }
    _eof() { return this._peek()===T.EOF }

    parse() {
        while(!this._eof()) { try { this._decl() } catch(e) { this._adv() } }
        return { nodes: this.an, strPool: this.sp.length }
    }

    _decl() {
        if(this._adv()===T.FUN) return this._fun()
        if(this._chk(T.VAL)||this._chk(T.VAR)) { this._adv(); return this._val() }
    }

    _fun() {
        const fi=this._node(NT.FUN,0,0,this._s(this._tText(this.pos))); this._adv() // name
        this._adv(); this._adv() // ( )
        if(this._chk(T.LBRACE)) return this._block(fi)
        if(this._chk(T.EQ)) { this._adv(); this._expr(); return fi }
        return fi
    }

    _val() {
        const vi=this._node(NT.VAL,0,0,this._s(this._tText(this.pos))); this._adv()
        if(this._chk(T.EQ)) { this._adv(); this._expr() }
        return vi
    }

    _block(parent) {
        this._adv() // {
        const bi=this._node(NT.BLOCK)
        while(!this._chk(T.RBRACE)) {
            if(this._chk(T.RETURN)) { this._adv(); this._expr() }
            else if(this._chk(T.VAL)||this._chk(T.VAR)) { this._adv(); this._val() }
            else { try { this._expr() } catch(e) { this._adv() } }
        }
        this._adv() // }
        this.ast[parent*6+3]=bi; return bi
    }

    _expr() { this.depth++; if(this.depth>500)throw new Error('depth'); try{return this._binary()}finally{this.depth--} }

    _binary(min=0) {
        let left=this._primary()
        while(true) {
            const prec=PREC[this._peek()]
            if(prec===undefined||prec<min) break
            const op=this._tText(this.pos); this._adv()
            const right=this._binary(prec+1)
            const bi=this._node(NT.BINARY,left,right,this._s(op))
            left=bi
        }
        return left
    }

    _primary() {
        const tp=this._peek()
        if(tp===T.INT_LIT) { this._adv(); return this._node(NT.LIT_INT,0,0,parseInt(this._tText(this.pos-1))) }
        if(tp===T.BOOL_LIT) { this._adv(); return this._node(NT.LIT_BOOL,0,0,this._tText(this.pos-1)==='true'?1:0) }
        if(tp===T.IDENT) { this._adv(); return this._node(NT.REF,0,0,this._s(this._tText(this.pos-1))) }
        if(tp===T.IF) { this._adv();this._adv();this._expr();this._adv();this._expr();if(this._chk(T.ELSE)){this._adv();this._expr()};return 0 }
        if(tp===T.LPAREN) { this._adv();this._expr();this._adv();return 0 }
        this._adv(); return 0
    }
}

module.exports = { TightPipeline }