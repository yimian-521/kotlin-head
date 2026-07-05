package com.qitong.head.eventbus

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * EventBus 三种通道 + 事件循环 —— v0.8.1 Node.js 能力适配
 *
 * 通道类型（专业模组）：
 *   EventChannel   = EventEmitter：一发多收，订阅者独立处理
 *   StreamChannel  = Stream/pipe：数据经 transform 链，链式串联
 *   WorkerChannel  = Worker Pool：一发一收，分发给空闲 worker
 *
 * 主线程是事件循环（像 Node.js event loop），只收频道消息+派发，永不被 I/O 阻塞。
 * 异步 I/O 走独立线程池，读完通过 "file" 频道敲门。
 * 依赖图解析通过 "dep" 频道联动 HED/TDL/进程树。
 * 即时广播接替走 "error" 频道。
 */

// ─── 消息 ───
data class Event(
    val channel: String,
    val type: String,
    val payload: Any? = null,
    val timestamp: Long = System.currentTimeMillis()
)

// ─── 通道接口 ───
interface EventHandler {
    fun onEvent(event: Event)
}

interface StreamTransform<I, O> {
    fun transform(input: I): O
}

interface WorkTask<R> {
    fun execute(): R
}

// ─── 事件通道：一发多收 ───
class EventChannel(val name: String) {
    private val handlers = mutableListOf<EventHandler>()

    fun subscribe(handler: EventHandler) { handlers.add(handler) }
    fun unsubscribe(handler: EventHandler) { handlers.remove(handler) }

    fun emit(type: String, payload: Any? = null) {
        val event = Event(channel = name, type = type, payload = payload)
        for (h in handlers) {
            try { h.onEvent(event) } catch (_: Exception) { /* 一个订阅者崩不影响其他 */ }
        }
    }

    fun subscriberCount() = handlers.size
}

// ─── 流式通道：数据经 transform 链，上一个输出是下一个输入 ───
class StreamChannel<T>(val name: String) {
    private val transforms = mutableListOf<StreamTransform<T, T>>()

    fun pipe(transform: StreamTransform<T, T>): StreamChannel<T> {
        transforms.add(transform)
        return this
    }

    fun process(input: T): T {
        var current = input
        for (t in transforms) {
            current = t.transform(current)
        }
        return current
    }

    fun pipeCount() = transforms.size
}

// ─── 工作通道：一发一收，任务分发给空闲 worker ───
class WorkerChannel<R>(val name: String, workers: Int = 4) {
    private val pendingTasks = ConcurrentLinkedQueue<Pair<WorkTask<R>, (R) -> Unit>>()
    private val executor = Executors.newFixedThreadPool(workers)

    fun submit(task: WorkTask<R>, callback: (R) -> Unit) {
        pendingTasks.add(task to callback)
        executor.submit {
            val pair = pendingTasks.poll() ?: return@submit
            try {
                val result = pair.first.execute()
                pair.second(result)
            } catch (e: Exception) {
                // 工作异常广播到 error 频道供接替
                EventBus.emitTo("error", "worker_crashed", mapOf("channel" to name, "error" to e.message))
            }
        }
    }

    fun shutdown() { executor.shutdown() }
}

// ─── EventBus 全局单例 ───
object EventBus {
    private val eventChannels = ConcurrentHashMap<String, EventChannel>()
    private val streamChannels = ConcurrentHashMap<String, StreamChannel<*>>()
    private val workerChannels = ConcurrentHashMap<String, WorkerChannel<*>>()

    // ─── 事件频道 ───
    fun eventChannel(name: String): EventChannel =
        eventChannels.getOrPut(name) { EventChannel(name) }

    fun subscribe(channel: String, handler: EventHandler) {
        eventChannel(channel).subscribe(handler)
    }

    fun emitTo(channel: String, type: String, payload: Any? = null) {
        eventChannel(channel).emit(type, payload)
    }

    // ─── 流式频道 ───
    fun <T> streamChannel(name: String): StreamChannel<T> {
        @Suppress("UNCHECKED_CAST")
        return streamChannels.getOrPut(name) { StreamChannel<T>(name) } as StreamChannel<T>
    }

    fun <T> pipe(channel: String, transform: StreamTransform<T, T>): StreamChannel<T> {
        return streamChannel<T>(channel).pipe(transform)
    }

    fun <T> processStream(channel: String, input: T): T {
        return streamChannel<T>(channel).process(input)
    }

    // ─── 工作频道 ───
    fun <R> workerChannel(name: String, workers: Int = 4): WorkerChannel<R> {
        @Suppress("UNCHECKED_CAST")
        return workerChannels.getOrPut(name) { WorkerChannel<R>(name, workers) } as WorkerChannel<R>
    }

    fun <R> submitWork(channel: String, task: WorkTask<R>, callback: (R) -> Unit, workers: Int = 4) {
        workerChannel<R>(channel, workers).submit(task, callback)
    }

    // ─── 状态查询 ───
    fun channelCount() = eventChannels.size + streamChannels.size + workerChannels.size
    fun shutdown() { workerChannels.values.forEach { it.shutdown() } }
}