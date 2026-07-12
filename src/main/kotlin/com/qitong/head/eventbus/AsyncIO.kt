package com.qitong.head.eventbus

import com.qitong.head.headstd.HMap
import java.io.File
import java.util.concurrent.Executors

object AsyncIO {
    private var ioPool = Executors.newFixedThreadPool(4)
    var maxFileSize: Int = 5 * 1024 * 1024  // 5MB上限
    var threadCount: Int = 4
        set(v) { ioPool = Executors.newFixedThreadPool(v); field = v }

    fun readFile(path: String, fileId: String? = null) {
        ioPool.submit {
            try {
                val file = File(path)
                if (file.length() > maxFileSize) {
                    val m = HMap<String, Any>(); m.put("path", path); m.put("error", "文件过大: ${file.length()/1024}KB > ${maxFileSize/1024}KB")
                    EventBus.emitTo("error", "file_too_large", m)
                    return@submit
                }
                val content = file.readText()
                val m = HMap<String, Any>(); m.put("path", path); m.put("fileId", fileId ?: path); m.put("content", content); m.put("size", content.length)
                EventBus.emitTo("file", "file_ready", m)
            } catch (e: Exception) {
                val m = HMap<String, Any>(); m.put("path", path); m.put("fileId", fileId ?: path); m.put("error", e.message ?: "unknown")
                EventBus.emitTo("error", "file_read_error", m)
            }
        }
    }

    fun readFiles(paths: List<String>) {
        paths.forEachIndexed { i, path -> readFile(path, "batch_$i") }
    }

    fun shutdown() { ioPool.shutdown() }
}