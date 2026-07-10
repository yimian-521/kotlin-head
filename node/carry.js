// kotlin-head Node.js — 携程因子 (Carry Factor)
// 调用方发"携程"→立即拿占位符→因子自己完成行程→结果自动就位

const { Lexer } = require('./lexer')
const { Parser } = require('./parser')

// === 携程因子: 动态影响并发行为的可变系数 ===
// 不是任务本身(原子)，是控制任务怎么跑的数值
const factor = {
    load: 0,        // 当前积压任务数
    max: 4,         // 最大并发——因子自动升降
    active: 0,      // 正在执行的任务数
    _pending: [],   // 等待队列

    /** 当前负载率 0.0~1.0 —— 外部监控用 */
    rate() { return this.active / this.max },

    /** 因子自调——积压≥2 →升上限, 积压=0 →降上限 */
    tune() {
        if (this.load >= 2 && this.max < 8) this.max++
        if (this.load === 0 && this.max > 1) this.max--
        this._drain()
    },
    _drain() {
        while (this.active < this.max && this._pending.length > 0) {
            const next = this._pending.shift()
            this.active++
            next()
        }
    },
    _done() {
        this.active--
        this.load = this._pending.length
        this.tune()
    }
}

// 全局行程队列——携程因子池
const pending = new Map()

function carry(src, onReady) {
    if (pending.has(src)) return pending.get(src).promise

    let resolve
    const promise = new Promise(r => { resolve = r })
    pending.set(src, { resolve, promise })

    const task = () => {
        try {
            const tokens = new Lexer(src).tokenize()
            const ast = new Parser(tokens).parseFile()
            const result = {
                success: true,
                tokens: tokens.map(t => ({ type: t.type, text: t.text, line: t.line, col: t.col })),
                declCount: ast.decls.length, diags: []
            }
            pending.delete(src)
            resolve(result)
            if (onReady) onReady(src, result)
        } catch (e) {
            pending.delete(src)
            resolve({ success: false, error: e.message })
        } finally {
            factor._done()
        }
    }

    factor.load++
    factor._drain()
    // 如果并发已满→进队列; 否则立即启动
    if (factor.active >= factor.max) {
        factor._pending.push(task)
    } else {
        factor.active++
        setImmediate(task)
    }
    return promise
}

async function carryAll(sources, onEach) {
    const results = {}
    await Promise.all(sources.map(src =>
        carry(src, onEach).then(r => { results[src] = r })))
    return results
}

module.exports = { carry, carryAll, factor }