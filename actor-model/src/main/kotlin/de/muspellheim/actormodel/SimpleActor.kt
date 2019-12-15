/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.actormodel

import java.util.concurrent.LinkedBlockingQueue

/** Base class for an actor with worker thread for inbox. */
abstract class SimpleActor(threadName: String) : Actor() {

    private val inbox = LinkedBlockingQueue<Any>()

    private val worker: () -> Unit = {
        while (!Thread.currentThread().isInterrupted) {
            try {
                val input = inbox.take()
                work(input)
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
}
