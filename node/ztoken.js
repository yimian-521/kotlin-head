// kotlin-head Node.js — 零拷贝 Token 存储
// 预分配 SharedArrayBuffer，Lexer→写入，Parser→读取，零复制

const T = require('./lexer').TokType

// token 编码: 每个 token 4 个 Uint16 — [type, line, col, textLen]
// 文本内容紧跟在后，最多存前 2048 个 token
const TOKENS_PER_CHUNK = 2048
const BYTES_PER_TOKEN = 4 * 2 // 4 x Uint16 = 8 bytes
const CHUNK_SIZE = TOKENS_PER_CHUNK * BYTES_PER_TOKEN + 32768 // +32KB text pool

class ZeroTokenBuffer {
    constructor() {
        this.buf = new SharedArrayBuffer(CHUNK_SIZE)
        this.types = new Uint16Array(this.buf, 0, TOKENS_PER_CHUNK)
        this.lines = new Uint16Array(this.buf, TOKENS_PER_CHUNK * 2, TOKENS_PER_CHUNK)
        this.cols  = new Uint16Array(this.buf, TOKENS_PER_CHUNK * 4, TOKENS_PER_CHUNK)
        this.textLens = new Uint16Array(this.buf, TOKENS_PER_CHUNK * 6, TOKENS_PER_CHUNK)
        this.textPool = new Uint8Array(this.buf, TOKENS_PER_CHUNK * 8, 32768)
        this.count = 0
        this.textOff = 0
    }
    reset() { this.count = 0; this.textOff = 0 }
    push(type, line, col, text) {
        if (this.count >= TOKENS_PER_CHUNK) return
        const i = this.count++
        this.types[i] = type; this.lines[i] = line; this.cols[i] = col
        const tl = Math.min(text.length, 255)
        this.textLens[i] = tl
        if (this.textOff + tl <= this.textPool.length) {
            for (let j = 0; j < tl; j++) this.textPool[this.textOff + j] = text.charCodeAt(j) & 0xFF
            this.textOff += tl
        }
    }
    getToken(i) {
        if (i >= this.count) return null
        const tl = this.textLens[i]
        let text = ''
        let off = 0
        for (let j = 0; j < i; j++) off += this.textLens[j]
        for (let j = 0; j < tl; j++) text += String.fromCharCode(this.textPool[off + j])
        return { type: this.types[i], text, line: this.lines[i], col: this.cols[i] }
    }
    // 零拷贝读取——Worker 直接读 buffer 不创建 JS 对象
    rawCount() { return this.count }
    rawType(i) { return Atomics.load(this.types, i) }
    rawLine(i) { return Atomics.load(this.lines, i) }
    rawCol(i)  { return Atomics.load(this.cols, i) }
}

// 预分配 token 池——复用，不每次 new
const pool = new ZeroTokenBuffer()

module.exports = { ZeroTokenBuffer, pool }