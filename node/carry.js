// kotlin-head Node.js — 携程因子 (Carry Factor)
// 调用方发"携程"→立即拿占位符→因子自己完成行程→结果自动就位

const { Lexer } = require('./lexer')
const { Parser } = require('./parser')

// === 携程因子 v2 (Carry Factor v2) ===
// 不设硬上限——通过压力感知自动收敛到系统最优并发
const factor = {
    load: 0, max: 4, active: 0, _pending: [],
    min: 1,  // 怠速保护

    // 压力感知：追踪最近 N 个任务的平均耗时
    _latencies: [],
    _avgLatency: 0,
    _latencyRising: false,  // 延迟在上升 = 系统饱和信号

    rate() { return this.active / this.max },

    /** 因子自调——积压→升，延迟上升→不升，空闲→降 */
    tune() {
        const shouldGrow = this.load >= 2 && !this._latencyRising
        const shouldShrink = this.load === 0 && this.max > this.min

        if (shouldGrow) this.max++
        if (shouldShrink) this.max--
        this._drain()
    },

    /** 记录任务耗时，计算延迟趋势 */
    _recordLatency(ms) {
        this._latencies.push(ms)
        if (this._latencies.length > 16) this._latencies.shift()
        const sum = this._latencies.reduce((a, b) => a + b, 0)
        const newAvg = sum / this._latencies.length
        // 延迟在上升 = 系统已饱和，不再扩容
        this._latencyRising = newAvg > this._avgLatency * 1.3 && this._latencies.length >= 4
        this._avgLatency = newAvg
    },

    _drain() {
        while (this.active < this.max && this._pending.length > 0) {
            const next = this._pending.shift()
            this.active++
            next()
        }
    },
    _done(startTime) {
        this.active--
        this.load = this._pending.length
        if (startTime) this._recordLatency(Date.now() - startTime)
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
        const _startTime = Date.now()
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
            factor._done(_startTime)
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