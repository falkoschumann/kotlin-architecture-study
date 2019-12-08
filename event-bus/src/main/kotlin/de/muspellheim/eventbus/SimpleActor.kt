/*
 * Architecture Study - Event Bus
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.eventbus

import java.util.concurrent.LinkedBlockingQueue

/** Base class for an actor with worker thread for inbox. */
abstract class SimpleActor(threadName: String) : Actor {

    override val outbox = Event<Any>()

    private val inbox = LinkedBlockingQueue<Any>()

    private val worker: () -> Unit = {
        while (!Thread.currentThread().isInterrupted) {
            try {
                val input = inbox.take()
                try {
                    work(input)
                } catch (e: Exception) {
                    handleException(e)
                }
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }

    init {
        val t = Thread(worker, threadName)
        t.isDaemon = true
        t.start()
    }

    override fun receive(message: Any) {
        try {
            inbox.put(message)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }

    private fun handleException(e: Exception) {
        Thread.currentThread()
            .uncaughtExceptionHandler
            .uncaughtException(Thread.currentThread(), e)
    }
}
