package com.qitong.head.eventbus

import java.io.File
import java.util.concurrent.Executors

object AsyncIO {
    private val ioPool = Executors.newFixedThreadPool(4)

    fun readFile(path: String, fileId: String? = null) {
        ioPool.submit {
            try {
                val content = File(path).readText()
                EventBus.emitTo("file", "file_ready", mapOf(
                    "path" to path,
                    "fileId" to (fileId ?: path),
                    "content" to content,
                    "size" to content.length
                ))
            } catch (e: Exception) {
                EventBus.emitTo("error", "file_read_error", mapOf(
                    "path" to path,
                    "fileId" to (fileId ?: path),
                    "error" to e.message
                ))
            }
        }
    }

    fun readFiles(paths: List<String>) {
        paths.forEachIndexed { i, path -> readFile(path, "batch_$i") }
    }

    fun shutdown() { ioPool.shutdown() }
}