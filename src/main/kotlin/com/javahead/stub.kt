package com.javahead

class JavaFileReport(val path: String = "", val diags: List<String> = emptyList())

object JavaHead {
    fun mount() {}
    fun unmount() {}
    val channel: JavaDetectionChannel = JavaDetectionChannel()
}

class JavaDetectionChannel {
    fun scan(path: String): List<JavaFileReport> = emptyList()
}
