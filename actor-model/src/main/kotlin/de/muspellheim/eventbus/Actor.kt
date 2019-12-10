/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.eventbus

/** An actor. */
interface Actor {

    val outbox: Event<Any>

    fun receive(message: Any)
    fun work(message: Any)
}

fun EventBus.registerActor(actor: Actor) {
    this.subscribe { actor.receive(it) }
    actor.outbox.addHandler { this.publish(it) }
}
