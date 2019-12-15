/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.actormodel

import de.muspellheim.shared.Event

/** An actor. */
abstract class Actor {

    val outbox = Event<Any>()

    abstract fun receive(message: Any)
    abstract fun work(message: Any)
}
