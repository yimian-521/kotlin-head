package com.qitong.head.process

/**
 * 注解处理器身份标签 —— v0.8.0-a
 *
 * 挂在注解处理器类上，指挥官看标签认小弟（像军队看军服）。
 * 不需要 AST 相似度重计算。
 *
 * 用法：
 *   @ProcessorTag("crud-generator")
 *   class DaoAnnotationProcessor { ... }
 *
 *   @ProcessorTag("serializer")
 *   class JsonAnnotationProcessor { ... }
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class ProcessorTag(val value: String)
