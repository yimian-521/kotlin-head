package com.qitong.head

/**
 * DevMode —— 开发者模式接口。
 * 
 * 主仓库（kotlin-head）只看到这个接口。
 * 实现在 devmode-internal.jar 中：
 *   - public 版：devformat + CRC，不加密
 *   - private 版：devformat → devcrypt → devseal，三层全上
 * 
 * 每层独立容错——读盘任何一层失败，返回默认值，不抛异常。
 * 编译器核心完全不知道存储层发生了什么。
 */
interface DevMode {

    /**
     * 初始化。返回 DevMode 实例。
     * 如果 jar 不存在或加载失败 → 返回 NoopDevMode（全部降级为无状态模式）。
     */
    companion object {
        fun boot(): DevMode {
            return try {
                // 尝试加载 private/public 实现
                Class.forName("com.qitong.internal.DevModeImpl")
                    .getDeclaredConstructor()
                    .newInstance() as DevMode
            } catch (_: Exception) {
                NoopDevMode()
            }
        }
    }

    /** 存。失败静默——不打断调用方。 */
    fun store(key: String, data: ByteArray)

    /** 读。失败返回 null——调用方按默认值处理。 */
    fun read(key: String): ByteArray?

    /** 删。失败静默。 */
    fun delete(key: String)
}

/**
 * 降级实现：全部操作都是空操作。
 * jar 不存在 → 编译器照样跑，只是不存状态。
 */
class NoopDevMode : DevMode {
    override fun store(key: String, data: ByteArray) {}
    override fun read(key: String): ByteArray? = null
    override fun delete(key: String) {}
}
