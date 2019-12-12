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

/** A store. */
@Singleton
class CounterStore @Inject constructor(dispatcher: Dispatcher) :
    ReduceStore<Counter>(Counter(0, false), dispatcher) {

    // TODO run in background

    override fun reduce(state: Counter, action: Any): Counter {
        return when (action) {
            is IncreaseCounterAction -> {
                val value = state.value + action.amount
                state.copy(value = value, isDecreasable = value > 0)
            }
            is DecreaseCounterAction -> {
                val value = max(0, state.value - action.amount)
                state.copy(value = value, isDecreasable = value > 0)
            }
            else -> state
        }
    }
}
