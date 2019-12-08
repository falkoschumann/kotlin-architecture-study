/*
 * Architecture Study - Model View ViewModel
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter.mvvm

import java.lang.Integer.max

/** A simple domain service with state. */
class CounterService {

    var value = 0
        private set

    fun increment() {
        value++
    }

    fun decrement() {
        value = max(0, value - 1)
    }
}
