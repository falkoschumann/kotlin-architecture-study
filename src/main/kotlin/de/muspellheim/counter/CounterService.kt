/*
 * Architecture Study
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

/** A simple service with state. */
class CounterService {

    var value = 0
        private set

    fun increment() {
        value++
    }

    fun decrement() {
        value--
    }
}
