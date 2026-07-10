// kotlin-head Node.js — 完整 Kotlin Lexer (移植自 Lexer.kt)
// 零依赖，纯 JavaScript

const TokType = {
  FUN:'FUN',VAL:'VAL',VAR:'VAR',CLASS:'CLASS',DATA:'DATA',
  IF:'IF',ELSE:'ELSE',RETURN:'RETURN',WHEN:'WHEN',FOR:'FOR',WHILE:'WHILE',
  OBJECT:'OBJECT',INTERFACE:'INTERFACE',ENUM:'ENUM',COMPANION:'COMPANION',OVERRIDE:'OVERRIDE',
  IMPORT:'IMPORT',PACKAGE:'PACKAGE',AS:'AS',TRY:'TRY',CATCH:'CATCH',FINALLY:'FINALLY',
  INT_LIT:'INT_LIT',STR_LIT:'STR_LIT',BOOL_LIT:'BOOL_LIT',IDENT:'IDENT',
  PLUS:'PLUS',MINUS:'MINUS',STAR:'STAR',SLASH:'SLASH',
  AND:'AND',OR:'OR',NOT:'NOT',
  EQ:'EQ',EQEQ:'EQEQ',BANGEQ:'BANGEQ',LT:'LT',GT:'GT',LTEQ:'LTEQ',GTEQ:'GTEQ',ELVIS:'ELVIS',
  LPAREN:'LPAREN',RPAREN:'RPAREN',LBRACE:'LBRACE',RBRACE:'RBRACE',LBRACK:'LBRACK',RBRACK:'RBRACK',
  COLON:'COLON',COMMA:'COMMA',DOT:'DOT',ARROW:'ARROW',SEMICOLON:'SEMICOLON',AT:'AT',
  EOF:'EOF'
}

const KEYWORDS = {
  fun:TokType.FUN,val:TokType.VAL,var:TokType.VAR,class:TokType.CLASS,data:TokType.DATA,
  if:TokType.IF,else:TokType.ELSE,return:TokType.RETURN,when:TokType.WHEN,for:TokType.FOR,while:TokType.WHILE,
  object:TokType.OBJECT,interface:TokType.INTERFACE,enum:TokType.ENUM,companion:TokType.COMPANION,override:TokType.OVERRIDE,
  import:TokType.IMPORT,package:TokType.PACKAGE,as:TokType.AS,try:TokType.TRY,catch:TokType.CATCH,finally:TokType.FINALLY,
  true:TokType.BOOL_LIT,false:TokType.BOOL_LIT
}

class Token {
  constructor(type, text, line, col) { this.type=type; this.text=text; this.line=line; this.col=col }
}

class Lexer {
  constructor(src) {
    this.src=src; this.i=0; this.line=1; this.col=1
  }
  peek(off=0) { return this.src[this.i+off] }
  advance() { const c=this.src[this.i++]; if(c!==undefined)this.col++; return c }
  tok(type,text,line,col) { return new Token(type,text,line,col) }

  skipLineComment() { while(this.i<this.src.length&&this.src[this.i]!=='\n')this.i++ }
  skipBlockComment() {
    this.i++
    while(this.i<this.src.length) {
      if(this.src[this.i]==='*'&&this.src[this.i+1]==='/'){this.i+=2;this.col+=2;return}
      if(this.src[this.i]==='\n'){this.line++;this.col=1} else this.col++
      this.i++
    }
  }

  readString(line,col) {
    this.i++; const sb=[]
    while(this.i<this.src.length&&this.src[this.i]!=='"'){
      if(this.src[this.i]==='\\'){this.i++;sb.push('\\')}
      sb.push(this.src[this.i]);this.i++;this.col++
    }
    if(this.i<this.src.length)this.i++
    return this.tok(TokType.STR_LIT,'"'+sb.join('')+'"',line,col)
  }

  readNumber(line,col) {
    const sb=[]
    while(this.i<this.src.length&&this.src[this.i]>='0'&&this.src[this.i]<='9'){sb.push(this.src[this.i]);this.advance()}
    return this.tok(TokType.INT_LIT,sb.join(''),line,col)
  }

  readIdent(line,col) {
    const sb=[]
    let c
    while(this.i<this.src.length&&((c=this.src[this.i],(c>='a'&&c<='z')||(c>='A'&&c<='Z')||(c>='0'&&c<='9')||c==='_'))){sb.push(c);this.advance()}
    const text=sb.join('')
    return this.tok(KEYWORDS[text]||TokType.IDENT,text,line,col)
  }

  readEq(line,col) { this.advance(); return this.peek()==='=' ? (this.advance(),this.tok(TokType.EQEQ,'==',line,col)) : this.tok(TokType.EQ,'=',line,col) }
  readBang(line,col) { this.advance(); return this.peek()==='=' ? (this.advance(),this.tok(TokType.BANGEQ,'!=',line,col)) : this.tok(TokType.NOT,'!',line,col) }
  readLt(line,col) { this.advance(); return this.peek()==='=' ? (this.advance(),this.tok(TokType.LTEQ,'<=',line,col)) : this.tok(TokType.LT,'<',line,col) }
  readGt(line,col) { this.advance(); return this.peek()==='=' ? (this.advance(),this.tok(TokType.GTEQ,'>=',line,col)) : this.tok(TokType.GT,'>',line,col) }
  readQ(line,col) { this.advance(); return this.peek()===':' ? (this.advance(),this.tok(TokType.ELVIS,'?:',line,col)) : this.tok(TokType.IDENT,'?',line,col) }

  tokenize() {
    const tokens=[]
    while(this.i<this.src.length) {
      const line=this.line,col=this.col
      const c=this.peek()
      if(c===' '||c==='\t') { this.advance(); continue }
      if(c==='\n') { this.advance(); this.line++; this.col=1; continue }
      if(c==='/') {
        this.advance()
        if(this.peek()==='/') { this.skipLineComment(); continue }
        if(this.peek()==='*') { this.skipBlockComment(); continue }
        tokens.push(this.tok(TokType.SLASH,'/',line,col))
        continue
      }
      if(c==='@') { this.advance(); tokens.push(this.tok(TokType.AT,'@',line,col)); continue }
      if(c==='"') { tokens.push(this.readString(line,col)); continue }
      if(c>='0'&&c<='9') { tokens.push(this.readNumber(line,col)); continue }
      if((c>='a'&&c<='z')||(c>='A'&&c<='Z')||c==='_') { tokens.push(this.readIdent(line,col)); continue }
      if(c==='+') { this.advance(); tokens.push(this.tok(TokType.PLUS,'+',line,col)); continue }
      if(c==='-') { this.advance(); tokens.push(this.peek()==='>'?(this.advance(),this.tok(TokType.ARROW,'->',line,col)):this.tok(TokType.MINUS,'-',line,col)); continue }
      if(c==='*') { this.advance(); tokens.push(this.tok(TokType.STAR,'*',line,col)); continue }
      if(c==='&') { this.advance(); tokens.push(this.peek()==='&'?(this.advance(),this.tok(TokType.AND,'&&',line,col)):this.tok(TokType.IDENT,'&',line,col)); continue }
      if(c==='|') { this.advance(); tokens.push(this.peek()==='|'?(this.advance(),this.tok(TokType.OR,'||',line,col)):this.tok(TokType.IDENT,'|',line,col)); continue }
      if(c==='(') { this.advance(); tokens.push(this.tok(TokType.LPAREN,'(',line,col)); continue }
      if(c===')') { this.advance(); tokens.push(this.tok(TokType.RPAREN,')',line,col)); continue }
      if(c==='{') { this.advance(); tokens.push(this.tok(TokType.LBRACE,'{',line,col)); continue }
      if(c==='}') { this.advance(); tokens.push(this.tok(TokType.RBRACE,'}',line,col)); continue }
      if(c==='[') { this.advance(); tokens.push(this.tok(TokType.LBRACK,'[',line,col)); continue }
      if(c===']') { this.advance(); tokens.push(this.tok(TokType.RBRACK,']',line,col)); continue }
      if(c===':') { this.advance(); tokens.push(this.tok(TokType.COLON,':',line,col)); continue }
      if(c===',') { this.advance(); tokens.push(this.tok(TokType.COMMA,',',line,col)); continue }
      if(c==='.') { this.advance(); tokens.push(this.tok(TokType.DOT,'.',line,col)); continue }
      if(c===';') { this.advance(); tokens.push(this.tok(TokType.SEMICOLON,';',line,col)); continue }
      if(c==='=') { tokens.push(this.readEq(line,col)); continue }
      if(c==='!') { tokens.push(this.readBang(line,col)); continue }
      if(c==='<') { tokens.push(this.readLt(line,col)); continue }
      if(c==='>') { tokens.push(this.readGt(line,col)); continue }
      if(c==='?') { tokens.push(this.readQ(line,col)); continue }
      this.advance()
    }
    tokens.push(new Token(TokType.EOF,'',this.line,this.col))
    return tokens
  }
}

module.exports = { Lexer, TokType, Token }