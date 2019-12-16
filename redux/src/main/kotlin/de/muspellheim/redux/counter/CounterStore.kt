/*
 * Architecture Study - Redux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.redux.counter

import de.muspellheim.redux.Reducer
import de.muspellheim.redux.Store
import kotlin.math.max

/** Reducers. */
val reducer: Reducer<Counter> = { state: Counter, action: Any ->
    when (action) {
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

/** Create Redux store. */
fun createStore(): Store<Counter> {
    return Store(reducer, Counter(0, true))
}
