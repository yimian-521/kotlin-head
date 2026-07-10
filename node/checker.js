// kotlin-head Node.js — TypeChecker (移植自 TypeChecker.kt)
// 作用域栈 +符号注册 +引用查找 +基本类型推断

class TypeChecker {
    constructor() {
        this.global = new Map()
        this.scopes = [this.global]
        this.diags = []
        this.loopDepth = 0
    }

    check(ast) {
        this.diags = []
        this.scopes = [this.global]
        for (const decl of (ast.decls || [])) this._checkDecl(decl)
        return this.diags
    }

    // === 声明 ===
    _checkDecl(decl) {
        if (decl.type === 'Class') {
            this._reg(decl.name, 'CLASS', decl)
            this.scopes.push(new Map())
            for (const m of (decl.members || [])) this._checkDecl(m)
            this.scopes.pop()
        } else if (decl.type === 'Fun') {
            this._reg(decl.name, 'FUN', decl)
            this.scopes.push(new Map())
            for (const p of (decl.params || [])) this._reg(p.name, 'PARAM', decl, p.type)
            if (decl.body) this._bind(decl.body)
            this.scopes.pop()
        } else if (decl.type === 'Val') {
            if (decl.value) this._bind(decl.value)
            this._reg(decl.name, 'VAL', decl, decl.type)
        }
    }

    // === 表达式绑定 ===
    _bind(expr) {
        if (!expr || !expr.type) return
        switch (expr.type) {
            case 'Ref': {
                if (!this._lookup(expr.name) && expr.name !== 'it')
                    this._err('未定义的引用: '+expr.name, expr.span?.start)
                break
            }
            case 'Binary': this._bind(expr.left); this._bind(expr.right); this._checkBinary(expr); break
            case 'Call': this._bind(expr.target); for (const a of (expr.args||[])) this._bind(a); break
            case 'Prefix': this._bind(expr.operand); break
            case 'If': this._bind(expr.cond); this._bind(expr.thenB); if (expr.elseB) this._bind(expr.elseB); break
            case 'When': if (expr.subject) this._bind(expr.subject)
                for (const b of (expr.branches||[])) { this._bind(b.cond); this._bind(b.body) }
                if (expr.elseB) this._bind(expr.elseB); break
            case 'Return': if (expr.value) this._bind(expr.value); break
            case 'Lambda':
                this.scopes.push(new Map())
                for (const p of (expr.params||[])) this._reg(p.name, 'PARAM', expr, p.type)
                this._bind(expr.body)
                this.scopes.pop(); break
            case 'Block':
                for (const s of (expr.stmts||[])) {
                    if (s.type==='Fun'||s.type==='Val'||s.type==='Class') this._checkDecl(s)
                    else if (s.type) this._bind(s)
                }; break
            case 'LitInt': case 'LitStr': case 'LitBool': break  // 叶子
        }
    }

    _checkBinary(expr) {
        const l = this._infer(expr.left), r = this._infer(expr.right)
        const arith = ['+','-','*','/','%']
        if (arith.includes(expr.op) && (l==='Boolean'||r==='Boolean'))
            this._err('Boolean不能参与算术: '+expr.op, expr.span?.start)
    }

    _infer(expr) {
        if (!expr) return null
        if (expr.type==='LitBool') return 'Boolean'
        if (expr.type==='LitInt') return 'Int'
        if (expr.type==='LitStr') return 'String'
        return null
    }

    // === 符号表 ===
    _reg(name, kind, node, type=null) {
        if (name.startsWith('?')) return
        const scope = this.scopes[this.scopes.length-1]
        if (scope.has(name)) this._err('重复声明: '+name, node.span?.start)
        else scope.set(name, {name, kind, type})
    }
    _lookup(name) {
        for (let i=this.scopes.length-1; i>=0; i--) {
            const s = this.scopes[i].get(name)
            if (s) return s
        }
        return null
    }

    _err(msg, pos) { this.diags.push({ level:'ERROR', msg, pos }) }
    _warn(msg, pos) { this.diags.push({ level:'WARN', msg, pos }) }
}

module.exports = { TypeChecker }