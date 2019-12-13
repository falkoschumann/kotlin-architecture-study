/*
 * Architecture Study - Redux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.redux.counter

import de.muspellheim.redux.Dispatcher
import javax.inject.Inject
import javax.inject.Singleton

/** A reduce store. */
@Singleton
class CounterReduceStore @Inject constructor(dispatcher: Dispatcher, val manager: CounterManager) :
    ReduceStore<Counter>(Counter(0, false), dispatcher) {

    override fun reduce(state: Counter, action: Any): Counter {
        return when (action) {
            is IncreaseCounterAction -> {
                manager.increase(state.value, action.amount)
                state
            }
            is DecreaseCounterAction -> {
                manager.decrease(state.value, action.amount)
                state
            }
            is CounterChangedAction -> {
                state.copy(value = action.newValue, isDecreasable = action.newValue > 0)
            }
            else -> state
        }
    }
}
