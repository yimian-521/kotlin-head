package com.qitong.head.eventbus

import com.qitong.head.headstd.HList
import com.qitong.head.headstd.HMap

data class Dependency(
    val name: String,
    val version: String?,
    val importedBy: String
)

data class DependencyConflict(
    val dep: String,
    val versions: Set<String>,
    val files: HList<String>
)

object DependencyGraph {
    private val deps = HMap<String, HList<Dependency>>()
    private val fileDeps = HMap<String, HList<Dependency>>()

    @Volatile private var currentSnapshot: StagingData? = null
    private val stageBuffer = HMap<String, HList<Dependency>>()

    class StagingData(
        val deps: HMap<String, HList<Dependency>>,
        val fileDeps: HMap<String, HList<Dependency>>
    )

    fun snapshot(): StagingData {
        val depsCopy = HMap<String, HList<Dependency>>()
        deps.forEach { k, v -> depsCopy.put(k, v) }
        val fileCopy = HMap<String, HList<Dependency>>()
        fileDeps.forEach { k, v -> fileCopy.put(k, v) }
        currentSnapshot = StagingData(depsCopy, fileCopy)
        stageBuffer.clear()
        return currentSnapshot!!
    }

    fun stage(): HMap<String, HList<Dependency>> = stageBuffer

    fun commit() {
        stageBuffer.forEach { path, depList ->
            val existing = deps.getOrPut(path) { HList<Dependency>() }
            depList.forEach { d ->
                if (existing.none { it.name == d.name && it.version == d.version }) existing.add(d)
            }
        }
        stageBuffer.clear()
        currentSnapshot = null
    }

    fun registerImport(importPath: String, sourceFile: String, version: String? = null) {
        val dep = Dependency(name = importPath, version = version, importedBy = sourceFile)
        if (currentSnapshot != null) {
            stageBuffer.getOrPut(importPath) { HList<Dependency>() }.add(dep)
            stageBuffer.getOrPut(sourceFile) { HList<Dependency>() }.add(dep)
        } else {
            deps.getOrPut(importPath) { HList<Dependency>() }.add(dep)
            fileDeps.getOrPut(sourceFile) { HList<Dependency>() }.add(dep)
        }
    }

    fun getDepsForFile(file: String): HList<Dependency> =
        (currentSnapshot?.fileDeps?.get(file) ?: fileDeps.get(file)) ?: HList()

    fun detectConflicts(): HList<DependencyConflict> {
        val conflicts = HList<DependencyConflict>()
        deps.forEach { importPath, depList ->
            val versions = mutableSetOf<String>()
            depList.forEach { it.version?.let { v -> versions.add(v) } }
            if (versions.size > 1) {
                val files = HList<String>()
                depList.forEach { files.add(it.importedBy) }
                val c = DependencyConflict(dep = importPath, versions = versions, files = files)
                conflicts.add(c)
                EventBus.emitTo("dep", "conflict_detected", c)
            }
        }
        return conflicts
    }

    fun resolveConflicts(): HMap<String, String> {
        val resolutions = HMap<String, String>()
        val conflicts = detectConflicts()
        for (c in conflicts.toList()) {
            var best = ""
            var bestScore = -1
            for (v in c.versions) {
                val score = parseVersion(v)
                if (score > bestScore) { bestScore = score; best = v }
            }
            if (best.isEmpty()) continue
            resolutions.put(c.dep, best)
            EventBus.emitTo("dep", "conflict_resolved", mapOf(
                "dep" to c.dep, "chosen" to best,
                "alternatives" to (c.versions - best),
                "isolated_files" to c.files.toList().filter { f ->
                    fileDeps.get(f)?.any { dep -> dep.name == c.dep && dep.version != best } ?: false
                }
            ))
        }
        return resolutions
    }

    private fun parseVersion(v: String): Int = try {
        v.split(".").map { it.toInt() }.fold(0) { acc, n -> acc * 1000 + n }
    } catch (_: Exception) { 0 }

    fun totalDeps(): Int {
        var total = 0
        deps.values().forEach { total += it.size }
        return total
    }
    fun totalFiles() = fileDeps.size
}