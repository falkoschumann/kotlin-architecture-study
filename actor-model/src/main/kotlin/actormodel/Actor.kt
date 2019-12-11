/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.actormodel

import de.muspellheim.shared.Action

/** An actor. */
interface Actor {

    val outbox: Action<Any>

    fun receive(message: Any)
    fun work(message: Any)
}

fun EventBus.registerActor(actor: Actor) {
    this.subscribe { actor.receive(it) }
    actor.outbox += { this.publish(it!!) }
}
