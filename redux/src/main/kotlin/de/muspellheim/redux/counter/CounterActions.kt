/*
 * Architecture Study - Redux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.redux.counter

data class IncreaseCounterAction(val amount: Int = 1)
data class DecreaseCounterAction(val amount: Int = 1)
data class CounterChangedAction(val newValue: Int)
