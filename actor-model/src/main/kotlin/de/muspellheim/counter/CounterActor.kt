/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

import de.muspellheim.eventbus.SimpleActor

/** A simple domain object as actor. */
class CounterActor(val counter: Counter) : SimpleActor("Counter Actor") {

    override fun work(message: Any) {
        when (message) {
            is IncreaseCounterAction -> {
                counter.increase()
                outbox(CounterUpdatedEvent(counter.value))
            }
            is DecreaseCounterAction -> {
                counter.decrease()
                outbox(CounterUpdatedEvent(counter.value))
            }
        }
    }
}
