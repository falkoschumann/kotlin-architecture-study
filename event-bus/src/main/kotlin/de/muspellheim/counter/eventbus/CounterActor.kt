/*
 * Architecture Study - Event Bus
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter.eventbus

import java.lang.Integer.max

/** A simple domain object as actor. */
class CounterActor : SimpleActor("Counter Actor") {

    private var value = 0

    override fun work(message: Any) {
        when (message) {
            is IncreaseCounterAction -> increase()
            is DecreaseCounterAction -> decrease()
        }
    }

    private fun increase() {
        value++
        outbox.send(CounterUpdatedEvent(value))
    }

    private fun decrease() {
        value = max(0, value - 1)
        outbox.send(CounterUpdatedEvent(value))
    }
}
