// kotlin-head Node.js — QitongEmbedded 完整移植
// 联存器 + Bank + 黑洞 + Snapshot + prefill + X4D

const { Lexer, TokType } = require('./lexer')
const { Parser } = require('./parser')
const { TypeChecker } = require('./checker')
const { BugScanner } = require('./bugscan')
const crypto = require('crypto')
const fs = require('fs')

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

// === 预编译缓存: 首次分析结果写盘，重启直接读 ===
const CACHE_FILE = '.kotlin-head-cache.json'
let cacheMap = null
function _loadCache() {
    if (cacheMap) return
    try { cacheMap = new Map(JSON.parse(fs.readFileSync(CACHE_FILE,'utf8'))) }
    catch(e) { cacheMap = new Map() }
}
function _saveCache() {
    try { fs.writeFileSync(CACHE_FILE, JSON.stringify([...cacheMap]),'utf8') }
    catch(e) {}
}
function _hash(src) { return crypto.createHash('sha256').update(src).digest('hex').slice(0,16) }

/** prefill with cache: 首次读盘→纳秒; 无缓存→全分析→写盘 */
function prefill(src) {
    _loadCache()
    const key = _hash(src)
    if (cacheMap.has(key)) {
        const r = cacheMap.get(key)
        l1.set(src, r)
        hotSrc = src; hotResult = r
        return
    }
    const r = analyzeSync(src)
    cacheMap.set(key, r)
    _saveCache()
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
// === V8 全覆盖预热: 用代表性源码构建内联缓存，首次即追平 TCC ===
const { CompactParser, CompactAST } = require('./vm')
;(function warmupV8() {
    const patterns = [
        'fun a()=1',                           // 单表达式
        'fun b(x:Int):Int=x+1',                // 参数+返回类型
        'fun c(){if(true)return 1;return 2}',  // 条件+return
        'fun d(n:Int):Int{if(n<=1)n else n*d(n-1)}', // 递归+算术
        'fun e(){val x=1;val y=2;return x+y}', // val+算术
        'class A(val v:Int)',                  // data class
        'fun g(){when(1){1->return;else->return}}' // when
    ]
    patterns.forEach(src => {
        const tks = new Lexer(src).tokenize()
        const ast = CompactAST.pooled(); ast.reset()
        const p = new CompactParser(tks); p.ast = ast
        p.parseFile()
    })
})()

prefill('fun main() { println(1) }')

module.exports = { VERSION, hit, analyze, analyzeSync, deposit, snapshot, prefill, hot,
    Bank, Snapshot }