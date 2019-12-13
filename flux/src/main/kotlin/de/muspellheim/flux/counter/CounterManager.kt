/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux.counter

import de.muspellheim.flux.Dispatcher
import de.muspellheim.shared.DispatchQueue
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.max

class CounterManager @Inject constructor(private val dispatcher: Dispatcher) {

    fun increase(value: Int, amount: Int) {
        DispatchQueue.background {
            TimeUnit.SECONDS.sleep(1)
            val newValue = value + amount
            dispatcher.dispatch(CounterChangedAction(newValue))
        }
    }

    fun decrease(value: Int, amount: Int) {
        DispatchQueue.background {
            TimeUnit.SECONDS.sleep(1)
            val newValue = max(0, value - amount)
            dispatcher.dispatch(CounterChangedAction(newValue))
        }
    }
}
