/*
 * Architecture Study - Passive View
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.passiveview.counter

import java.lang.Integer.max
import java.util.concurrent.TimeUnit

/** A simple domain service with state. */
class CounterService {

    var value = 0
        private set

    fun increment() {
        TimeUnit.SECONDS.sleep(1)
        value++
    }

    fun decrement() {
        TimeUnit.SECONDS.sleep(1)
        value = max(0, value - 1)
    }
}
