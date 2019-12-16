/*
 * Architecture Study - Passive View
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.passiveview.counter

import java.lang.Integer.max

/** A simple domain service with state. */
class CounterService {

    private var _value = 0
    val value get() = _value

    fun increase() {
        _value++
    }

    fun decrease() {
        _value = max(0, _value - 1)
    }
}
