/*
 * Architecture Study - Event Bus
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

data class IncreaseCounterAction(val amount: Int = 1)
data class DecreaseCounterAction(val amount: Int = 1)

data class CounterUpdatedEvent(val newValue: Int)
