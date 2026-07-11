// kotlin-head Node.js — BugScanner (移植自 BugScanner.kt)
// 11条规则扫描常见 Kotlin 陷阱

class BugScanner {
    constructor() { this.findings = [] }

    scan(ast) {
        this.findings = []
        this._scanNode(ast)
        return this.findings
    }

    _add(rule, severity, msg, span) {
        this.findings.push({ rule, severity, message: msg, span })
    }

    _scanNode(node) {
        if (!node) return
        switch (node.type) {
            case 'File': for (const d of (node.decls||[])) this._scanNode(d); break
            case 'Class':
                this._tailrecOpen(node); this._dataCopyAnn(node); this._sealedReflect(node)
                for (const m of (node.members||[])) this._scanNode(m); break
            case 'Fun':
                this._crossinlineReturn(node); this._reifiedTrap(node); this._pubApiLeak(node)
                this._lazyThread(node); this._nonLocalReturn(node)
                if (node.body) this._scanExpr(node.body); break
            case 'Val':
                this._platformBang(node); this._delegateTrap(node)
                if (node.value) this._scanExpr(node.value); break
            case 'Block': for (const s of (node.stmts||[])) this._scanNode(s); break
            case 'If': this._scanExpr(node.cond); this._scanExpr(node.thenB); if (node.elseB) this._scanExpr(node.elseB); break
            case 'When': this._scanExpr(node); break
            case 'Return': if (node.value) this._scanExpr(node.value); break
            case 'Call': this._typeDegrade(node); this._scanExpr(node); break
            case 'Lambda': if (node.body) this._scanExpr(node.body); break
            case 'Binary': this._scanExpr(node); break
            case 'Member': this._scanNode(node.obj); break
        }
    }

    _scanExpr(expr) {
        if (!expr) return
        switch (expr.type) {
            case 'Binary':
                this._boolArith(expr); this._strCmp(expr)
                this._scanExpr(expr.left); this._scanExpr(expr.right); break
            case 'Call':
                this._scanExpr(expr.target); for (const a of (expr.args||[])) this._scanExpr(a); break
            case 'If': this._scanExpr(expr.cond); this._scanExpr(expr.thenB); if (expr.elseB) this._scanExpr(expr.elseB); break
            case 'When':
                if ((!expr.branches||expr.branches.length===0)&&!expr.elseB)
                    this._add('BS-EMPTY-WHEN','MEDIUM','空when表达式',expr.span?.start)
                if (expr.subject) this._scanExpr(expr.subject)
                for (const b of (expr.branches||[])) { this._scanExpr(b.cond); this._scanExpr(b.body) }
                if (expr.elseB) this._scanExpr(expr.elseB); break
            case 'Block': for (const s of (expr.stmts||[])) { if (s.type) this._scanNode(s) }; break
            case 'Return': if (expr.value) this._scanExpr(expr.value); break
        }
    }

    // 规则
    _boolArith(expr) {
        // 只检测+运算符(Boolean+Int最常见事故), -*/%几乎不跟Bool混用
        if (expr.op !== '+') return
        if (expr.left?.type==='LitBool'||expr.right?.type==='LitBool')
            this._add('BS-BOOL-ARITH','HIGH','Boolean参与算术: '+expr.op, expr.span?.start)
    }
    _strCmp(expr) {
        if (!['>','<','>=','<='].includes(expr.op)) return
        if ((expr.left?.type==='LitStr'&&expr.right?.type==='LitInt')||(expr.left?.type==='LitInt'&&expr.right?.type==='LitStr'))
            this._add('BS-STR-CMP','MEDIUM','String与Int比较: '+expr.op, expr.span?.start)
    }
    _tailrecOpen(cls) {
        for (const m of (cls.members||[])) {
            if (m.type==='Fun'&&(m.mods||[]).includes('tailrec')&&(m.mods||[]).includes('open'))
                this._add('KT-TAILREC-OPEN','HIGH','tailrec在open函数上无效', m.span?.start)
        }
    }
    _dataCopyAnn(cls) {
        if (!(cls.mods||[]).includes('data')) return
        const hasMemberAnn = (cls.members||[]).some(m => (m.type==='Fun'||m.type==='Val')&&(m.anns||[]).length>0)
        if (hasMemberAnn) this._add('KT-DATA-COPY-ANN','MEDIUM','data class成员注解copy不复制', cls.span?.start)
    }
    _sealedReflect(cls) {
        if ((cls.mods||[]).includes('sealed'))
            this._add('KT-SEALED-REFLECT','LOW','sealed class反射跨模块不完整', cls.span?.start)
    }
    _crossinlineReturn(fn) {
        if (!(fn.mods||[]).includes('inline')) return
        if (fn.body) this._checkReturnInLambda(fn.body, true)
    }
    _reifiedTrap(fn) {
        const hasReified = (fn.params||[]).some(p => (p.type||'').includes('reified'))
        if ((fn.mods||[]).includes('inline') && hasReified)
            this._add('KT-REIFIED-TRAP','LOW','inline+reified跨边界假错误', fn.span?.start)
    }
    _pubApiLeak(fn) {
        if ((fn.anns||[]).some(a=>a.name==='PublishedApi')&&(fn.mods||[]).includes('internal'))
            this._add('KT-PUBAPI-LEAK','MEDIUM','@PublishedApi internal二进制兼容问题', fn.span?.start)
    }
    _lazyThread(fn) { if (fn.body) this._checkLazy(fn.body) }
    _checkLazy(node) {
        if (!node) return
        if (node.type==='Block') { for (const s of (node.stmts||[])) this._checkLazy(s) }
        if (node.type==='Binary') { this._checkLazy(node.left); this._checkLazy(node.right) }
        if (node.type==='Call'&&node.target?.name==='lazy')
            this._add('KT-LAZY-THREAD','LOW','by lazy默认SYNCHRONIZED', node.span?.start)
    }
    _nonLocalReturn(fn) {
        if (!(fn.mods||[]).includes('inline')) return
        if (fn.body) this._checkReturnInLambda(fn.body, false)
    }
    _checkReturnInLambda(node, inLambda) {
        if (!node) return
        if (node.type==='Block') { for (const s of (node.stmts||[])) this._checkReturnInLambda(s, inLambda) }
        if (node.type==='Lambda') this._checkReturnInLambda(node.body, true)
        if (node.type==='Return'&&inLambda) this._add('KT-NONLOCAL-RETURN','MEDIUM','inline lambda中return非局部返回', node.span?.start)
    }
    _platformBang(valDecl) { if (valDecl.value) this._checkBang(valDecl.value) }
    _checkBang(expr) {
        if (!expr) return
        if (expr.type==='Binary'&&expr.op==='!!') this._add('KT-PLATFORM-BANG','HIGH','!!吞掉NPE根因', expr.span?.start)
        if (expr.type==='Call') for (const a of (expr.args||[])) this._checkBang(a)
        if (expr.type==='Block') for (const s of (expr.stmts||[])) if (s.type) this._checkBang(s)
    }
    _delegateTrap(valDecl) {
        if ((valDecl.mods||[]).includes('by'))
            this._add('KT-DELEGATE-TRAP','LOW','委托属性异常被包装为KNPE', valDecl.span?.start)
    }
    _typeDegrade(call) {
        const tn = call.target?.name || call.target?.member || ''
        if ((call.args||[]).length>=3&&tn.length>10)
            this._add('KT-TYPE-DEGRADE','LOW','复杂泛型调用可能退化为Any: '+tn, call.span?.start)
    }
}

module.exports = { BugScanner }