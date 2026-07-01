package com.qitong.internal

import com.qitong.head.DevMode
import com.qitong.head.internal.JsonUtil
import java.io.File
import java.util.zip.CRC32

/**
 * DevMode public 实现 —— devformat（JSON）+ CRC 校验，不加密。
 *
 * 存储路径：~/.kotlin-head/
 * ├── session.json    ← 当前交互状态
 * └── cache/          ← 按源文件 hash 存储分析结果
 *
 * 容错策略：每层独立降级。
 *   读 → 文件不存在？CRC 不匹配？JSON 损坏？→ 返回 null
 *   写 → 磁盘满了？权限不足？→ 静默失败
 * 编译器核心完全不知道存储层发生了什么。
 */
class DevModeImpl : DevMode {

    private val baseDir = File(System.getProperty("user.home"), ".kotlin-head")
    private val cacheDir = File(baseDir, "cache")

    init {
        baseDir.mkdirs()
        cacheDir.mkdirs()
    }

    // ─── 存储 ───
    override fun store(key: String, data: ByteArray) {
        try {
            val file = fileFor(key)
            // 格式：[json]\n[crc32十六进制]
            val json = JsonUtil.encode(mapOf("k" to key, "v" to String(data)))
            val crc = crc32(json.toByteArray())
            file.writeText("$json\n$crc")
        } catch (_: Exception) { /* 静默 */ }
    }

    // ─── 读取 ───
    override fun read(key: String): ByteArray? {
        return try {
            val file = fileFor(key)
            if (!file.exists()) return null

            val raw = file.readText()
            val splitAt = raw.lastIndexOf('\n')
            if (splitAt <= 0) return null

            val json = raw.substring(0, splitAt)
            val crcHex = raw.substring(splitAt + 1).trim()
            val expectedCrc = crcHex.toLongOrNull(16) ?: return null
            val actualCrc = crc32(json.toByteArray())

            if (expectedCrc != actualCrc) {
                // CRC 不匹配 → 文件损坏，清掉坏文件
                file.delete()
                return null
            }

            // 解析 JSON → 取 "v"
            val map = JsonUtil.decode(json) as? Map<*, *> ?: return null
            (map["v"] as? String)?.toByteArray()
        } catch (_: Exception) { null }
    }

    // ─── 删除 ───
    override fun delete(key: String) {
        try { fileFor(key).delete() } catch (_: Exception) {}
    }

    // ─── 内部工具 ───
    private fun fileFor(key: String): File {
        // session / config 直接映射，cache 前缀走子目录
        return if (key.startsWith("cache:")) {
            File(cacheDir, "${key.removePrefix("cache:").takeLast(32)}.json")
        } else {
            File(baseDir, "$key.json")
        }
    }

    private fun crc32(data: ByteArray): Long {
        val crc = CRC32()
        crc.update(data)
        return crc.value
    }
}