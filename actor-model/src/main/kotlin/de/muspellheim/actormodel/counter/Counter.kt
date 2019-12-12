/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.actormodel.counter

import java.util.concurrent.TimeUnit

/** A simple domain model with state. */
class Counter {

    var value = 0
        private set

    fun increase(amount: Int = 1) {
        TimeUnit.SECONDS.sleep(1)
        value += amount
    }

    fun decrease(amount: Int = 1) {
        TimeUnit.SECONDS.sleep(1)
        value = Integer.max(0, value - amount)
    }
}
