/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux.counter

import de.muspellheim.flux.Dispatcher
import javax.inject.Inject
import javax.inject.Singleton

data class IncreaseCounterAction(val amount: Int = 1)
data class DecreaseCounterAction(val amount: Int = 1)
data class CounterChangedAction(val newValue: Int)

/** An action factory. */
@Singleton
class CounterActions @Inject constructor(private val dispatcher: Dispatcher) {
    fun increase() = dispatcher.dispatch(IncreaseCounterAction())
    fun decrease() = dispatcher.dispatch(DecreaseCounterAction())
    fun changed(newValue: Int) = dispatcher.dispatch(CounterChangedAction(newValue))
}
