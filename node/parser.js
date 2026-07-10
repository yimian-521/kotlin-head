// kotlin-head Node.js — 递归下降 Parser (移植自 Parser.kt)
// 零依赖，纯 JavaScript

const { TokType } = require('./lexer')
const T = TokType

// === AST 节点工厂 ===
const P = (line,col) => ({line,col})
const S = (s,e) => ({start:s,end:e||s})

const node = (type,props) => Object.assign({type},props)
const N = {
  File: (pkg,decls,span) => node('File',{pkg,decls,span}),
  Fun: (name,params,ret,body,mods,span) => node('Fun',{name,params,ret,body,mods,span}),
  Val: (name,type,value,mods,span) => node('Val',{name,type,value,mods,span}),
  Class: (name,mods,members,span) => node('Class',{name,mods,members,span}),
  Param: (name,type,span) => node('Param',{name,type,span}),
  Block: (stmts,span) => node('Block',{stmts,span}),
  Return: (value,span) => node('Return',{value,span}),
  If: (cond,thenB,elseB,span) => node('If',{cond,thenB,elseB,span}),
  When: (subject,branches,elseB,span) => node('When',{subject,branches,elseB,span}),
  WhenBranch: (cond,body,span) => node('WhenBranch',{cond,body,span}),
  Binary: (left,op,right,span) => node('Binary',{left,op,right,span}),
  Prefix: (op,operand,span) => node('Prefix',{op,operand,span}),
  Call: (target,args,span) => node('Call',{target,args,span}),
  Ref: (name,span) => node('Ref',{name,span}),
  Member: (obj,member,safe,span) => node('Member',{obj,member,safe,span}),
  Lambda: (params,body,span) => node('Lambda',{params,body,span}),
  LitInt: (value,span) => node('LitInt',{value,span}),
  LitStr: (value,span) => node('LitStr',{value,span}),
  LitBool: (value,span) => node('LitBool',{value,span}),
  Object: (name,isCompanion,members,span) => node('Object',{name,isCompanion,members,span})
}

// === 优先级表 (STANDARD) ===
const PREC = {}
PREC[T.OR]=1; PREC[T.AND]=2; PREC[T.EQEQ]=3; PREC[T.BANGEQ]=3
PREC[T.LT]=4; PREC[T.GT]=4; PREC[T.LTEQ]=4; PREC[T.GTEQ]=4
PREC[T.ELVIS]=5; PREC[T.PLUS]=7; PREC[T.MINUS]=7; PREC[T.STAR]=8; PREC[T.SLASH]=8

// === 声明开始判断 ===
const DECL_START = new Set([
  T.FUN,T.VAL,T.VAR,T.CLASS,T.OBJECT,T.INTERFACE,T.ENUM,T.COMPANION,T.AT,T.DATA
])

class Parser {
  constructor(tokens) {
    this.tokens = tokens
    this.pos = 0
    this.diags = []
    this.exprDepth = 0
  }

  peek(off=0) { return this.tokens[this.pos+off]||this.tokens[this.tokens.length-1] }
  advance() { return this.tokens[this.pos++]||this.tokens[this.tokens.length-1] }
  isEof() { return this.pos>=this.tokens.length||this.peek().type===T.EOF }
  check(t) { return !this.isEof()&&this.peek().type===t }
  match(t) { return !this.isEof()&&this.peek().type===t }
  matchText(s) { return this.peek().text===s }
  expect(t) { if(!this.check(t)) throw new Error(`expected ${t}, got ${this.peek().type} at ${this.peek().line}:${this.peek().col}`) }
  span(s) { return S(s,this.tokens[this.pos-1]?.pos||s) }

  warn(msg) { this.diags.push({level:'WARN',msg,pos:this.peek().pos}) }

  // === 顶层 ===
  parseFile() {
    this.diags=[]
    this._skipPackage()
    this._skipImports()
    const decls=[]
    while(!this.isEof()){
      try { const d=this._parseDecl(); if(d) decls.push(d) }
      catch(e) { this.warn(e.message); this._skipToNextDecl() }
    }
    return N.File(null,decls,S(P(1,1),P(1,1)))
  }

  _skipPackage() {
    if(this.check(T.PACKAGE)) { this.advance() }
    while(!this.isEof()){
      const t=this.peek().type
      if(t===T.IMPORT||t===T.AT||DECL_START.has(t)) break
      this.advance()
    }
  }

  _skipImports() {
    while(this.check(T.IMPORT)) {
      this.advance()
      while(!this.isEof()&&this.peek().type!==T.IMPORT&&!DECL_START.has(this.peek().type)) this.advance()
    }
  }

  _skipToNextDecl() {
    let skip=0
    while(!this.isEof()&&skip<500){
      skip++
      if(DECL_START.has(this.peek().type)) break
      if(this.peek().type===T.LBRACE){
        let d=1; this.advance()
        while(d>0&&!this.isEof()&&skip<500){const tt=this.advance().type;if(tt===T.LBRACE)d++;if(tt===T.RBRACE)d--;skip++}
      }else this.advance()
    }
  }

  // === 声明 ===
  _parseDecl() {
    // 修饰符
    while(this.peek().type===T.IDENT&&['private','internal','public','protected','suspend','override','open','abstract','lateinit','const'].includes(this.peek().text)) this.advance()
    if(this.matchText('data')) {
      this.advance(); this.expect(T.CLASS); this.advance()
      const name=this.advance().text
      const start=this.tokens[this.pos-2].pos
      const members=this.check(T.LPAREN) ? this._parsePrimaryCtorMembers() : []
      return N.Class(name,['data','public'],members,this.span(start))
    }
    if(this.check(T.FUN)) return this._parseFun()
    if(this.check(T.VAL)||this.check(T.VAR)) return this._parseVal()
    if(this.check(T.OBJECT)||this.check(T.COMPANION)) return this._parseObject()
    this.warn(`unknown decl: ${this.peek().text}`)
    this.advance()
    return null
  }

  _parsePrimaryCtorMembers() {
    this.expect(T.LPAREN); this.advance()
    const ms=[]
    while(!this.check(T.RPAREN)){
      if(this.check(T.COMMA)){this.advance();continue}
      if(this.check(T.VAL)||this.check(T.VAR)) this.advance()
      const name=this.advance().text
      let type=null
      if(this.check(T.COLON)){this.advance();type=this.advance().text}
      if(this.check(T.EQ)){this.advance();this._skipDefaultExpr()}
      ms.push(N.Val(name,type,null,[],this.span(this.tokens[this.pos-1].pos)))
    }
    this.expect(T.RPAREN); this.advance()
    return ms
  }

  _skipDefaultExpr() {
    let d=0
    while(!this.isEof()){const t=this.peek().type;if(t===T.LPAREN){this.advance();d++}else if(t===T.RPAREN&&d>0){this.advance();d--}else if(t===T.RPAREN||t===T.COMMA)break;else this.advance()}
  }

  _parseFun() {
    const mods=[]
    while(['suspend','override','open','abstract','private','internal','public'].includes(this.peek().text)){mods.push(this.advance().text)}
    this.expect(T.FUN); const kw=this.advance()
    const name=this.advance().text
    this.expect(T.LPAREN); this.advance()
    const params=[]
    while(!this.check(T.RPAREN)){
      if(this.check(T.COMMA)){this.advance();continue}
      const pName=this.advance().text; let pType=null
      if(this.check(T.COLON)){this.advance();pType=this.advance().text}
      params.push(N.Param(pName,pType,S(P(this.tokens[this.pos-1].line,this.tokens[this.pos-1].col))))
      if(this.check(T.EQ)){this.advance();this._skipDefaultExpr()}
    }
    this.expect(T.RPAREN); this.advance()
    let ret=null
    if(this.check(T.COLON)){this.advance();ret=this.advance().text}
    const body=this.check(T.EQ)?this._parseExprBody():this.check(T.LBRACE)?this._parseBlock():null
    return N.Fun(name,params,ret,body,mods,this.span(kw.pos))
  }

  _parseExprBody() { this.advance(); return this._parseExpr() }
  _parseBlock() {
    const start=this.advance().pos
    const stmts=[]
    while(!this.check(T.RBRACE)){
      if(this.check(T.VAL)||this.check(T.VAR)) stmts.push(this._parseVal())
      else if(this.check(T.RETURN)){
        const rk=this.advance(); const v=this.check(T.RBRACE)?null:this._parseExpr()
        stmts.push(N.Return(v,this.span(rk.pos)))
      } else { try{stmts.push(this._parseExpr())}catch(e){this.advance()} }
    }
    const end=this.advance().pos
    return N.Block(stmts,S(start,end))
  }

  _parseVal() {
    const mods=[]
    while(['override','private','internal','public','open','abstract','lateinit','const'].includes(this.peek().text)){mods.push(this.advance().text)}
    const kw=this.advance()
    const name=this.advance().text; let type=null
    if(this.check(T.COLON)){this.advance();type=this.advance().text}
    let val=null
    if(this.check(T.EQ)){this.advance();val=this._parseExpr()}
    return N.Val(name,type,val,mods,this.span(kw.pos))
  }

  _parseObject() {
    const isCompanion=this.check(T.COMPANION); if(isCompanion)this.advance()
    const kw=this.advance()
    let name=null
    if(this.check(T.IDENT)&&this.tokens[this.pos+1]?.type===T.LBRACE){name=this.advance().text}
    const members=[]
    if(this.check(T.LBRACE)){
      this.advance()
      while(!this.check(T.RBRACE)){
        try{const d=this._parseDecl();if(d)members.push(d)}catch(e){this.advance()}
      }
      this.advance()
    }
    return N.Object(name,isCompanion,members,this.span(kw.pos))
  }

  // === 表达式 ===
  _parseExpr() {
    this.exprDepth++
    if(this.exprDepth>500) throw new Error('expr depth overflow')
    try { return this._parseBinary() } finally { this.exprDepth-- }
  }

  _parseBinary(minPrec=0) {
    let left=this._parsePrimary()
    while(true){
      const prec=PREC[this.peek().type]
      if(prec===undefined||prec<minPrec) break
      const op=this.advance().text
      const right=this._parseBinary(prec+1)
      left=N.Binary(left,op,right,S(left.span.start,right.span.end))
    }
    return left
  }

  _parsePrimary() {
    const t=this.peek()
    const tp=t.type
    if(tp===T.INT_LIT){this.advance();return N.LitInt(parseInt(t.text),S(t.pos))}
    if(tp===T.STR_LIT){this.advance();return N.LitStr(t.text,S(t.pos))}
    if(tp===T.BOOL_LIT){this.advance();return N.LitBool(t.text==='true',S(t.pos))}
    if(tp===T.NOT){const s=this.advance().pos;return N.Prefix('!',this._parseExpr(),S(s))}
    if(tp===T.IF) return this._parseIf()
    if(tp===T.WHEN) return this._parseWhen()
    if(tp===T.RETURN){const r=this.advance();return N.Return(this.check(T.RBRACE)?null:this._parseExpr(),S(r.pos))}
    if(tp===T.IDENT) return this._parseIdentChain()
    if(tp===T.LPAREN) return this._parseParenOrLambda()
    if(tp===T.LBRACE) return this._parseLambda()
    this.advance()
    return N.LitBool(true,S(t.pos))
  }

  _parseIf() {
    const ik=this.advance(); this.expect(T.LPAREN); this.advance()
    const cond=this._parseExpr(); this.expect(T.RPAREN); this.advance()
    const then=this.check(T.LBRACE)?this._parseBlock():this._parseExpr()
    let els=null
    if(this.check(T.ELSE)){this.advance();els=this.check(T.LBRACE)?this._parseBlock():this._parseExpr()}
    return N.If(cond,then,els,this.span(ik.pos))
  }

  _parseWhen() {
    const wk=this.advance(); const subj=this.check(T.LPAREN)?(this.advance(),this._parseExpr(),this.expect(T.RPAREN),this.advance(),subj):null
    this.expect(T.LBRACE); this.advance()
    const branches=[]
    let elseB=null
    while(!this.check(T.RBRACE)){
      if(this.matchText('else')){this.advance();this.expect(T.ARROW);this.advance();elseB=this._parseExpr();break}
      const cond=this._parseExpr(); this.expect(T.ARROW); this.advance()
      branches.push(N.WhenBranch(cond,this._parseExpr(),S(t.pos)))
      if(this.check(T.COMMA)) this.advance()
    }
    this.advance()
    return N.When(subj,branches,elseB,this.span(wk.pos))
  }

  _parseIdentChain() {
    const start=this.peek().pos
    let expr=N.Ref(this.advance().text,S(start))
    if(this.check(T.LPAREN)){expr=this._parseCall(expr)}
    while(this.check(T.DOT)||(this.peek().text==='?'&&this.tokens[this.pos+1]?.type===T.DOT)){
      const safe=this.peek().text==='?'; if(safe) this.advance()
      this.advance() // DOT
      if(this.check(T.IDENT)){
        const m=this.advance().text
        let right=N.Ref(m,S(start))
        if(this.check(T.LPAREN)) right=this._parseCall(right)
        expr=N.Member(expr,m,safe,S(expr.span.start,right.span.end))
      } else break
    }
    return expr
  }

  _parseCall(target) {
    this.expect(T.LPAREN); this.advance()
    const args=[]
    while(!this.check(T.RPAREN)){
      if(this.check(T.COMMA)){this.advance();continue}
      if(this.check(T.IDENT)&&this.tokens[this.pos+1]?.type===T.EQ){this.advance();this.advance();args.push(this._parseExpr());continue}
      args.push(this._parseExpr())
    }
    this.expect(T.RPAREN); this.advance()
    if(this.check(T.LBRACE)) args.push(this._parseLambda())
    return N.Call(target,args,S(target.span.start))
  }

  _parseParenOrLambda() {
    this.advance() // (
    if(this.check(T.RPAREN)){this.advance();return this._parseLambdaTail()}
    const inner=this._parseExpr()
    this.expect(T.RPAREN); this.advance()
    return this.check(T.ARROW)?this._parseLambdaTail(inner):inner
  }

  _parseLambda() { const s=this.advance().pos; return this._parseLambdaTail() }
  _parseLambdaTail(captured) {
    const s=P(this.tokens[this.pos-1]?.line||1,this.tokens[this.pos-1]?.col||1)
    if(this.check(T.ARROW)) this.advance()
    const body=this._parseExpr()
    this.expect(T.RBRACE); this.advance()
    return N.Lambda(captured?[N.Param('it',null,S(s))]:[],body,S(s))
  }
}

module.exports = { Parser }