/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

import de.muspellheim.flux.Dispatcher
import javax.inject.Inject

data class IncreaseCounterAction(val amount: Int = 1)
data class DecreaseCounterAction(val amount: Int = 1)

class CounterActions @Inject constructor(private val dispatcher: Dispatcher<Any>) {
    fun increase() = dispatcher.dispatch(IncreaseCounterAction())
    fun decrease() = dispatcher.dispatch(DecreaseCounterAction())
}
