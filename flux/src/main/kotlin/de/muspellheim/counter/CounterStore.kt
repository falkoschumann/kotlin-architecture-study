/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

import de.muspellheim.flux.Dispatcher
import de.muspellheim.flux.ReduceStore
import javax.inject.Inject
import kotlin.math.max

/** A simple store. */
class CounterStore @Inject constructor(dispatcher: Dispatcher<Any>) :
    ReduceStore<Counter>(Counter(0, false), dispatcher) {

    override fun reduce(state: Counter, action: Any): Counter {
        return when (action) {
            is IncreaseCounterAction -> {
                val value = state.value + 1
                state.copy(value = value, isDecreasable = value > 0)
            }
            is DecreaseCounterAction -> {
                val value = max(0, state.value - 1)
                state.copy(value = value, isDecreasable = value > 0)
            }
            else -> state
        }
    }
}
