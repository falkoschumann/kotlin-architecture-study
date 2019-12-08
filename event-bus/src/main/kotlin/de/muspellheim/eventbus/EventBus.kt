/*
 * Architecture Study - Event Bus
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.eventbus

import java.util.concurrent.LinkedBlockingQueue

/** Asynchronously Event Bus. */
class EventBus(val name: String) {

    private var subscribers: List<(Any) -> Unit> = listOf()
    private val queue = LinkedBlockingQueue<Any>()

    private val dispatcher = Runnable {
        while (!Thread.currentThread().isInterrupted) {
            try {
                val event = queue.take()
                subscribers.forEach {
                    try {
                        it(event)
                    } catch (e: Exception) {
                        Thread.currentThread()
                            .uncaughtExceptionHandler
                            .uncaughtException(Thread.currentThread(), e)
                    }
                }
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }

    init {
        val t = Thread(dispatcher, name)
        t.isDaemon = true
        t.start()
    }

    fun subscribe(subscriber: (Any) -> Unit) {
        subscribers = subscribers + subscriber
    }

    fun unsubscribe(subscriber: (Any) -> Unit) {
        subscribers = subscribers - subscriber
    }

    fun publish(event: Any) {
        try {
            queue.put(event)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }

    companion object {
        private val default = EventBus("Default Event Bus")
        fun default() = default
    }
}
