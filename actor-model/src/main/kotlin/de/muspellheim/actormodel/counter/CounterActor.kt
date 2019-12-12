/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.actormodel.counter

import de.muspellheim.actormodel.SimpleActor

/** An actor. */
class CounterActor(private val counter: Counter) : SimpleActor("Counter Actor") {

    override fun work(message: Any) {
        when (message) {
            is IncreaseCounterAction -> {
                counter.increase(message.amount)
                outbox(CounterUpdatedEvent(counter.value))
            }
            is DecreaseCounterAction -> {
                counter.decrease(message.amount)
                outbox(CounterUpdatedEvent(counter.value))
            }
        }
    }
}
