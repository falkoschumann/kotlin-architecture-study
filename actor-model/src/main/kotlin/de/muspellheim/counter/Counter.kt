/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

class Counter {

    var value = 0
        private set

    fun increase() {
        value++
    }

    fun decrease() {
        value = Integer.max(0, value - 1)
    }
}
