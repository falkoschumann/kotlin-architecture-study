/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.actormodel.counter

import kotlin.math.max

/** A simple domain model with state. */
class Counter {

    private var _value = 0
    val value get() = _value

    fun increase() {
        _value++
    }

    fun decrease() {
        _value = max(0, _value - 1)
    }
}
