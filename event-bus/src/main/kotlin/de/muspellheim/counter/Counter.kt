/*
 * Architecture Study - Event Bus
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

import java.lang.Integer.max
import java.util.concurrent.LinkedBlockingQueue

/** A simple domain object. */
class Counter {

    var value = 0
        private set

    private val inbox = LinkedBlockingQueue<CounterAction>()

    private val worker = Runnable {
        while (!Thread.currentThread().isInterrupted) {
            try {
                val action = inbox.take()
                try {
                    val event = work(action)
                    EventBus.default().publish(event)
                } catch (e: Exception) {
                    Thread.currentThread()
                        .uncaughtExceptionHandler
                        .uncaughtException(Thread.currentThread(), e)
                }
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }

    init {
        val t = Thread(worker, "Counter Worker")
        t.isDaemon = true
        t.start()

        EventBus.default().subscribe { push(it) }
    }

    private fun push(event: Any) {
        if (event !is CounterAction) return

        try {
            inbox.put(event)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }

    internal fun work(action: CounterAction): CounterEvent {
        when (action) {
            is IncreaseCounterAction -> {
                value++
                return CounterUpdatedEvent(value)
            }
            is DecreaseCounterAction -> {
                value = max(0, value - 1)
                return CounterUpdatedEvent(value)
            }
        }
        return object : CounterEvent {}
    }
}
