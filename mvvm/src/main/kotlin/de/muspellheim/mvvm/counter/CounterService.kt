/*
 * Architecture Study - Model View ViewModel
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.mvvm.counter

import java.lang.Integer.max
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/** A simple domain service with state. */
@Singleton
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
