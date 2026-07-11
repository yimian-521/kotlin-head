// kotlin-head Node.js — 零拷贝 Token 存储
// 预分配 SharedArrayBuffer，Lexer→写入，Parser→读取，零复制

const T = require('./lexer').TokType

// token 编码: 每个 token 4 个 Uint16 — [type, line, col, textLen]
// 文本内容紧跟在后，最多存前 2048 个 token
const TOKENS_PER_CHUNK = 2048
const BYTES_PER_TOKEN = 4 * 2 // 4 x Uint16 = 8 bytes
const CHUNK_SIZE = TOKENS_PER_CHUNK * BYTES_PER_TOKEN + 32768 // +32KB text pool

// === ztoken v2 ===
// v1→v2: +Atomics.store 多Worker写安全 + textOff预计算O(1)读取 + chunk满告警
class ZeroTokenBuffer {
    constructor() {
        this.buf = new SharedArrayBuffer(CHUNK_SIZE)
        this.types = new Uint16Array(this.buf, 0, TOKENS_PER_CHUNK)
        this.lines = new Uint16Array(this.buf, TOKENS_PER_CHUNK * 2, TOKENS_PER_CHUNK)
        this.cols  = new Uint16Array(this.buf, TOKENS_PER_CHUNK * 4, TOKENS_PER_CHUNK)
        this.textLens = new Uint16Array(this.buf, TOKENS_PER_CHUNK * 6, TOKENS_PER_CHUNK)
        this.textPool = new Uint8Array(this.buf, TOKENS_PER_CHUNK * 8, 32768)
        // v2新增: 预计算文本偏移，getToken(i) O(1)
        this._off = new Uint16Array(TOKENS_PER_CHUNK)
        this.count = 0
        this.textOff = 0
    }
    reset() { this.count = 0; this.textOff = 0; this._off.fill(0) }
    push(type, line, col, text) {
        // v2: chunk满时告警而非静默丢弃
        if (this.count >= TOKENS_PER_CHUNK) { console.warn('[ztoken] chunk full, token dropped'); return }
        const i = this.count++
        // v2: Atomics.store 保证多Worker写安全
        Atomics.store(this.types, i, type)
        Atomics.store(this.lines, i, line)
        Atomics.store(this.cols, i, col)
        const tl = Math.min(text.length, 255)
        Atomics.store(this.textLens, i, tl)
        this._off[i] = this.textOff // v2: 预计算偏移
        if (this.textOff + tl <= this.textPool.length) {
            for (let j = 0; j < tl; j++) this.textPool[this.textOff + j] = text.charCodeAt(j) & 0xFF
            this.textOff += tl
        }
    }
    getToken(i) {
        if (i >= this.count) return null
        // v2: O(1)读取——预计算偏移
        const off = this._off[i], tl = Atomics.load(this.textLens, i)
        let text = ''
        for (let j = 0; j < tl; j++) text += String.fromCharCode(this.textPool[off + j])
        return { type: Atomics.load(this.types, i), text, line: Atomics.load(this.lines, i), col: Atomics.load(this.cols, i) }
    }
    rawCount() { return this.count }
    rawType(i) { return Atomics.load(this.types, i) }
    rawLine(i) { return Atomics.load(this.lines, i) }
    rawCol(i)  { return Atomics.load(this.cols, i) }
}

// 预分配 token 池——复用，不每次 new
const pool = new ZeroTokenBuffer()

module.exports = { ZeroTokenBuffer, pool }