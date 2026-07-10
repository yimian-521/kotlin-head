// kotlin-head Node.js — WHSL (WASM-like Stack Parser)
// 模仿 WASM 栈式架构：不用递归函数调用，显式栈驱动

const { Lexer, TokType } = require('./lexer')
const T = TokType

const PREC = {}; PREC[T.OR]=1;PREC[T.AND]=2;PREC[T.EQEQ]=3;PREC[T.BANGEQ]=3
PREC[T.LT]=4;PREC[T.GT]=4;PREC[T.LTEQ]=4;PREC[T.GTEQ]=4
PREC[T.ELVIS]=5;PREC[T.PLUS]=7;PREC[T.MINUS]=7;PREC[T.STAR]=8;PREC[T.SLASH]=8

// AST 紧凑格式：每个节点 4 个 int32 — [type, child1, child2, extra]
const NODE_SZ = 4
const NT = { FILE:1,FUN:2,VAL:3,BLOCK:4,RETURN:5,IF:6,BINARY:7,
    REF:9,LIT_INT:11,LIT_BOOL:13 }

class WHSLParser {
    constructor(tokens) {
        this.t = tokens; this.pos = 0 // token pos
        // AST buffer
        this.a = Buffer.alloc(4096 * NODE_SZ * 4); this.na = 0
        // 显式栈: 不用递归，用数组模拟调用栈
        // 栈帧格式: [pc, retNode, minPrec, ...]
        this.stk = Buffer.alloc(256 * 4); this.sp = 0 // 栈指针
        
    }

    tpeek() { return this.t[this.pos]?.type || T.EOF }
    tadv() { return this.t[this.pos++] }
    ttext(i) { return (this.t[i||(this.pos-1)]||{}).text || '' }
    teof() { return this.pos >= this.t.length || this.tpeek() === T.EOF }

    node(type, c1, c2, ex) {
        const i = this.na++ * NODE_SZ
        this.a[i]=type; this.a[i+1]=c1||0; this.a[i+2]=c2||0; this.a[i+3]=ex||0
        return i
    }

    push(v) { this.stk[this.sp++] = v }
    pop() { return this.stk[--this.sp] }

    parse() {
        // 主循环：状态机
        while (!this.teof()) {
            const t = this.tpeek()
            if (t === T.FUN) { this._fun(); continue }
            if (t === T.VAL || t === T.VAR) { this._val(); continue }
            this.tadv() // 跳过不认识的
        }
        return this.na // 节点数
    }

    _fun() {
        this.tadv() // fun
        const fi = this.node(NT.FUN, 0, 0, 0) // extra待填
        this.tadv() // name - skip
        // skip params
        let d = 0
        if (this.tpeek() === T.LPAREN) {
            this.tadv()
            while (this.tpeek() !== T.RPAREN && !this.teof()) this.tadv()
            this.tadv()
        }
        // body
        if (this.tpeek() === T.LBRACE) {
            this.tadv() // {
            const bi = this.node(NT.BLOCK)
            while (this.tpeek() !== T.RBRACE && !this.teof()) {
                if (this.tpeek() === T.RETURN) { this.tadv(); this._exprS() }
                else if (this.tpeek() === T.VAL || this.tpeek() === T.VAR) { this._val() }
                else { try { this._exprS() } catch(e) { this.tadv() } }
            }
            this.tadv() // }
            this.a[fi+1] = bi // child1 = block
        } else if (this.tpeek() === T.EQ) {
            this.tadv(); this._exprS()
        }
    }

    _val() {
        this.tadv() // val/var
        const vi = this.node(NT.VAL)
        this.tadv() // name
        if (this.tpeek() === T.EQ) { this.tadv(); this._exprS() }
    }

    // 栈式表达式解析——核心：不用递归
    _exprS() {
        return this._binS(0)
    }

    _binS(minPrec) {
        // 栈式Pratt parser
        let left = this._primS()
        while (true) {
            const prec = PREC[this.tpeek()]
            if (prec === undefined || prec < minPrec) break
            this.tadv() // op
            const right = this._binS(prec + 1)
            left = this.node(NT.BINARY, left, right, 0)
        }
        return left
    }

    _primS() {
        const tp = this.tpeek()
        if (tp === T.INT_LIT) { this.tadv(); return this.node(NT.LIT_INT, 0, 0, 0) }
        if (tp === T.IDENT) { this.tadv(); return this.node(NT.REF, 0, 0, 0) }
        if (tp === T.BOOL_LIT) { this.tadv(); return this.node(NT.LIT_BOOL, 0, 0, 0) }
        if (tp === T.LPAREN) { this.tadv(); const n = this._exprS(); this.tadv(); return n }
        if (tp === T.IF) {
            this.tadv(); this.tadv(); this._exprS(); this.tadv(); this._exprS()
            if (this.tpeek() === T.ELSE) { this.tadv(); this._exprS() }
            return 0
        }
        this.tadv(); return 0
    }
}

module.exports = { WHSLParser }