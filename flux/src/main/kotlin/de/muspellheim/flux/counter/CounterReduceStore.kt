/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux.counter

import de.muspellheim.flux.Dispatcher
import de.muspellheim.flux.ReduceStore
import de.muspellheim.shared.DispatchQueue
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.max

/** A store. */
@Singleton
class CounterReduceStore @Inject constructor(dispatcher: Dispatcher) :
    ReduceStore<Counter>(Counter(0, false), dispatcher) {

    // TODO Extract manager for API calls

    override fun reduce(state: Counter, action: Any): Counter {
        return when (action) {
            is IncreaseCounterAction -> {
                DispatchQueue.background {
                    TimeUnit.SECONDS.sleep(1)
                    val value = state.value + action.amount
                    dispatcher.dispatch(CounterChangedAction(value))
                }
                state
            }
            is DecreaseCounterAction -> {
                DispatchQueue.background {
                    TimeUnit.SECONDS.sleep(1)
                    val value = max(0, state.value - action.amount)
                    dispatcher.dispatch(CounterChangedAction(value))
                }
                state
            }
            is CounterChangedAction -> {
                state.copy(value = action.newValue, isDecreasable = action.newValue > 0)
            }
            else -> state
        }
    }
}
