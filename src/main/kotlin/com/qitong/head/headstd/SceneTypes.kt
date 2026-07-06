package com.qitong.head.headstd

enum class HellType { SYNTAX, TYPE, LINK, MIXED, NONE }
enum class SceneType { LIGHT, MEDIUM, HEAVY, CRITICAL, DEFAULT }
data class SceneProfile(
    val hellType: HellType = HellType.NONE,
    val scale: Int = 0,
    val bugDensity: Float = 0f,
    val isBatch: Boolean = false,
    val isIncremental: Boolean = false
)
