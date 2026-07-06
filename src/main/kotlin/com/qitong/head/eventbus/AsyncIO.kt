package com.qitong.head.eventbus

import com.qitong.head.runtime.HMap
import java.io.File
import java.util.concurrent.Executors

object AsyncIO {
    private val ioPool = Executors.newFixedThreadPool(4)

    fun readFile(path: String, fileId: String? = null) {
        ioPool.submit {
            try {
                val content = File(path).readText()
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