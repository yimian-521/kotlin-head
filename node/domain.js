// kotlin-head Node.js — 域程 (Domain Thread)
// 每个源码文件一个独立的联存器/Bank/黑洞实例
// 域之间互不干扰——分析A不覆盖B的热缓存

const { Lexer } = require('./lexer')
const { Parser } = require('./parser')
const { TypeChecker } = require('./checker')
const { BugScanner } = require('./bugscan')

let domainId = 0

function createDomain() {
    const id = ++domainId
    let hotSrc = ''
    let hotResult = { success: true, bugs: [], diags: [], error: null }
    const l1 = new Map()
    const L1_MAX = 256

    function hit(src) { return src === hotSrc ? hotResult : null }

    function analyzeSync(src) {
        if (src === hotSrc) return hotResult
        if (l1.has(src)) { hotSrc = src; hotResult = l1.get(src); return hotResult }
        try {
            const tokens = new Lexer(src).tokenize()
            const ast = new Parser(tokens).parseFile()
            const diags = new TypeChecker().check(ast)
            const bugs = new BugScanner().scan(ast)
            const r = {
                success: true, bugs, diags: [...ast.diags||[], ...diags],
                tokens: tokens.map(t => ({type:t.type,text:t.text,line:t.line,col:t.col})),
                declCount: ast.decls.length, bugCount: bugs.length
            }
            l1.set(src, r); if (l1.size > L1_MAX) { l1.delete(l1.keys().next().value) }
            hotSrc = src; hotResult = r; return r
        } catch(e) { return { success: false, error: e.message } }
    }

    function analyze(src) {
        if (src === hotSrc) return hotResult
        if (l1.has(src)) { hotSrc = src; hotResult = l1.get(src); return hotResult }
        return hotResult
    }

    class Bank { constructor(src,res) { this.src=src; this.res=res }; result() { return this.res } }
    function deposit(src) { return new Bank(src, analyzeSync(src)) }

    class Snapshot { constructor(src,res) { this.src=src; this.res=res }; check(s) { return s===this.src?this.res:null } }
    function snapshot() { return new Snapshot(hotSrc, hotResult) }

    function prefill(src) { return analyzeSync(src) }
    function hot() { return hotResult }

    // 快速分析: 跳过TypeChecker和BugScanner，只做Lexer+Parser
    function analyzeFast(src) {
        if (src === hotSrc) return hotResult
        if (l1.has(src)) { hotSrc = src; hotResult = l1.get(src); return hotResult }
        try {
            const tokens = new Lexer(src).tokenize()
            const ast = new Parser(tokens).parseFile()
            const r = { success: true, bugs: [], diags: [],
                tokens: tokens.map(t => ({type:t.type,text:t.text,line:t.line,col:t.col})),
                declCount: ast.decls.length
            }
            l1.set(src, r); if (l1.size > L1_MAX) { l1.delete(l1.keys().next().value) }
            hotSrc = src; hotResult = r; return r
        } catch(e) { return { success: false, error: e.message } }
    }

    return { id, hit, analyze, analyzeSync, analyzeFast, deposit, snapshot, prefill, hot, Bank, Snapshot }
}

// 全局域管理器——按文件路由
const domains = new Map()
function of(file) {
    if (!domains.has(file)) domains.set(file, createDomain())
    return domains.get(file)
}
function stats() { return { domains: domains.size } }

module.exports = { createDomain, of, stats }