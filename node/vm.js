// kotlin-head Node.js — 紧凑虚拟机 (Compact VM)
// AST 不用对象，用 TypedArray + 索引 → 消对象分配 → 逼近 C

const { Lexer, TokType } = require('./lexer')
const T = TokType

// === 紧凑 AST 格式 ===
// 每个节点 6 个 uint16: [type, spanStart, spanEnd, child1, child2, extra]
// child1/child2 是子节点索引，extra 是额外数据(字符串池偏移等)
const NODE_SIZE = 6

class CompactAST {
    constructor(capacity = 4096) {
        this.buf = new Uint16Array(capacity * NODE_SIZE)
        this.strPool = []  // 字符串池——名字/字面量存这里
        this.next = 0
    }
    alloc(type) {
        const i = this.next++ * NODE_SIZE
        this.buf[i] = type; return i
    }
    setSpan(i, s, e) { this.buf[i+1]=s; this.buf[i+2]=e }
    setChild1(i, c) { this.buf[i+3]=c }
    setChild2(i, c) { this.buf[i+4]=c }
    setExtra(i, e) { this.buf[i+5]=e }
    intern(s) { const i=this.strPool.length; this.strPool.push(s); return i }
    str(i) { return this.strPool[this.buf[i+5]] || '' }
    type(i) { return this.buf[i] }

    // 节点类型枚举
    static T = {
        FILE:1, FUN:2, VAL:3, CLASS:4, BLOCK:5, RETURN:6, IF:7, BINARY:8,
        CALL:9, REF:10, LIT_INT:11, LIT_STR:12, LIT_BOOL:13, LAMBDA:14,
        PARAM:15, PREFIX:16, MEMBER:17, WHEN:18, OBJECT:19, EOF:0
    }
}

// === 紧凑 Parser (状态机，不创建JS对象) ===
const PREC = {}; PREC[T.OR]=1; PREC[T.AND]=2; PREC[T.EQEQ]=3; PREC[T.BANGEQ]=3
PREC[T.LT]=4; PREC[T.GT]=4; PREC[T.LTEQ]=4; PREC[T.GTEQ]=4
PREC[T.ELVIS]=5; PREC[T.PLUS]=7; PREC[T.MINUS]=7; PREC[T.STAR]=8; PREC[T.SLASH]=8

class CompactParser {
    constructor(tokens) {
        this.t = tokens; this.pos = 0; this.depth = 0
        this.ast = new CompactAST()
    }
    p(off=0) { return this.t[this.pos+off]||this.t[this.t.length-1] }
    adv() { return this.t[this.pos++] }
    eof() { return this.pos>=this.t.length||this.p().type===T.EOF }
    chk(tp) { return !this.eof()&&this.p().type===tp }
    txt() { return this.p().text }
    posInfo() { return {l:this.p().line,c:this.p().col} }

    parseFile() {
        const fi = this.ast.alloc(CompactAST.T.FILE)
        while(!this.eof()) { try { this._decl() } catch(e) {} }
        this.ast.setChild1(fi, this.ast.next)
        return this.ast
    }

    _decl() {
        const t = this.p()
        if (t.type===T.FUN) return this._fun()
        if (t.type===T.VAL||t.type===T.VAR) return this._val()
        if (this.txt()==='data') { this.adv(); this.adv(); this.adv(); return }
        this.adv()
    }

    _fun() {
        this.adv() // fun
        const fi = this.ast.alloc(CompactAST.T.FUN)
        const nameIdx = this.ast.intern(this.adv().text); this.ast.setExtra(fi, nameIdx)
        this.adv(); this.adv() // skip ( )
        if (this.chk(T.LBRACE)) return this._block(fi)
        if (this.chk(T.EQ)) { this.adv(); this._expr(); return fi }
        return fi
    }

    _val() {
        this.adv(); const vi = this.ast.alloc(CompactAST.T.VAL)
        const nameIdx = this.ast.intern(this.adv().text); this.ast.setExtra(vi, nameIdx)
        if (this.chk(T.EQ)) { this.adv(); this._expr() }
        return vi
    }

    _block(parent) {
        this.adv() // {
        const bi = this.ast.alloc(CompactAST.T.BLOCK)
        while(!this.chk(T.RBRACE)) {
            if (this.chk(T.RETURN)) { this.adv(); const ri=this.ast.alloc(CompactAST.T.RETURN); this._expr(); this.ast.setChild1(ri,0) }
            else if (this.chk(T.VAL)||this.chk(T.VAR)) this._val()
            else { try { this._expr() } catch(e) { this.adv() } }
        }
        if (this.chk(T.RBRACE)) this.adv()
        if (parent) this.ast.setChild1(parent, bi)
        return bi
    }

    _expr() {
        this.depth++; if (this.depth>500) throw new Error('depth')
        try { return this._binary() } finally { this.depth-- }
    }

    _binary(min=0) {
        let left = this._primary()
        while(true) {
            const prec = PREC[this.p().type]
            if(prec===undefined||prec<min) break
            const op = this.adv().text
            const right = this._binary(prec+1)
            const bi = this.ast.alloc(CompactAST.T.BINARY)
            this.ast.setExtra(bi, this.ast.intern(op))
            this.ast.setChild1(bi, left); this.ast.setChild2(bi, right)
            left = bi
        }
        return left
    }

    _primary() {
        const t = this.p()
        if (t.type===T.INT_LIT) { this.adv(); const li=this.ast.alloc(CompactAST.T.LIT_INT); this.ast.setExtra(li, parseInt(t.text)); return li }
        if (t.type===T.STR_LIT) { this.adv(); const si=this.ast.alloc(CompactAST.T.LIT_STR); this.ast.setExtra(si, this.ast.intern(t.text)); return si }
        if (t.type===T.BOOL_LIT) { this.adv(); const bi=this.ast.alloc(CompactAST.T.LIT_BOOL); this.ast.setExtra(bi, t.text==='true'?1:0); return bi }
        if (t.type===T.IDENT) { this.adv(); const ri=this.ast.alloc(CompactAST.T.REF); this.ast.setExtra(ri, this.ast.intern(t.text)); return ri }
        if (t.type===T.IF) { this.adv(); this.adv(); this._expr(); this.adv(); this._expr(); if(this.chk(T.ELSE)){this.adv();this._expr()}; return 0 }
        if (t.type===T.LPAREN) { this.adv(); this._expr(); this.adv(); return 0 }
        this.adv(); return 0
    }
}

module.exports = { CompactParser, CompactAST }