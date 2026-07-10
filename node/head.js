// kotlin-head Node.js — QitongEmbedded 完整移植
// 联存器 + Bank + 黑洞 + Snapshot + prefill + X4D

const { Lexer, TokType } = require('./lexer')
const { Parser } = require('./parser')
const { TypeChecker } = require('./checker')
const { BugScanner } = require('./bugscan')

const VERSION = '1.1.0-node'

// === 黑洞: hotResult 永不为空 ===
let hotSrc = ''
let hotResult = { success: true, version: VERSION, bugs: [], diags: [], error: null }

// === L1: 256条 LRU (用 Map 模拟, JS Map保持插入顺序) ===
const l1 = new Map()
const L1_MAX = 256

// === 联存器命中 (=== 在JS中比较字符串原始值) ===
function hit(src) {
    if (src === hotSrc) return hotResult
    return null
}

// === 银行模式 ===
class Bank {
    constructor(src, res) { this.src = src; this.res = res }
    result() { return this.res }
}
function deposit(src) {
    const r = analyzeSync(src)
    return new Bank(src, r)
}

// === Snapshot 口袋 ===
class Snapshot {
    constructor(src, res) { this.src = src; this.res = res }
    check(s) { return s === this.src ? this.res : null }
}
function snapshot() { return new Snapshot(hotSrc, hotResult) }

// === prefill 预填 ===
function prefill(src) {
    const r = analyzeSync(src)
    l1.set(src, r)
    if (l1.size > L1_MAX) { const first = l1.keys().next().value; l1.delete(first) }
    hotSrc = src; hotResult = r
}

// === analyzeSync: 全编译 (Lexer→Token输出, 无Parser) ===
function analyzeSync(src) {
    if (src === hotSrc) return hotResult
    if (l1.has(src)) {
        const r = l1.get(src)
        hotSrc = src; hotResult = r
        return r
    }
    try {
    const lexer = new Lexer(src)
    const tokens = lexer.tokenize()
    const parser = new Parser(tokens)
    const ast = parser.parseFile()
    const checker = new TypeChecker()
    const checkDiags = checker.check(ast)
    const bugs = new BugScanner().scan(ast)
    const r = {
        success: true, version: VERSION,
        bugs: bugs, diags: [...parser.diags, ...checkDiags],
        tokens: tokens.map(t => ({ type: t.type, text: t.text, line: t.line, col: t.col })),
        ast: { declCount: ast.decls.length, checkDiags: checkDiags.length, bugCount: bugs.length },
        error: null
    }
    l1.set(src, r)
    if (l1.size > L1_MAX) { const first = l1.keys().next().value; l1.delete(first) }
    hotSrc = src; hotResult = r
    return r
  } catch(e) {
    return { success: false, version: VERSION, bugs: [], diags: [], tokens: [], error: e.message }
  }
}

// === analyze: 不阻塞版本 ===
function analyze(src) {
    if (src === hotSrc) return hotResult
    if (l1.has(src)) {
        const r = l1.get(src)
        hotSrc = src; hotResult = r
        return r
    }
    // 未命中 → 返回黑洞兜底
    return hotResult
}

// === 黑洞 直接读 ===
function hot() { return hotResult }

// === 预热 ===
prefill('fun main() { println(1) }')

module.exports = { VERSION, hit, analyze, analyzeSync, deposit, snapshot, prefill, hot,
    Bank, Snapshot }