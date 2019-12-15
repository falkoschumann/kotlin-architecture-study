/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux.counter

import de.muspellheim.flux.Dispatcher
import de.muspellheim.flux.ReduceStore
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.max

/** A reduce store. */
@Singleton
class CounterReduceStore @Inject constructor(dispatcher: Dispatcher) :
    ReduceStore<Counter>(Counter(0, true), dispatcher) {

    override fun reduce(state: Counter, action: Any): Counter {
        return when (action) {
            is IncreaseCounterAction -> {
                val value = state.value + 1
                Counter(value, false)
            }
            is DecreaseCounterAction -> {
                val value = max(0, state.value - 1)
                val isDecreaseDisabled = value == 0
                Counter(value, isDecreaseDisabled)
            }
            else -> state
        }
    }
}
