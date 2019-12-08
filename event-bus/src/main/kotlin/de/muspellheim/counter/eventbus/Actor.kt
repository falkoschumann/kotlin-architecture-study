/*
 * Architecture Study - Event Bus
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter.eventbus

/** An actor. */
interface Actor {

    val outbox: Event<Any>

    fun receive(message: Any)
    fun work(message: Any)
}
