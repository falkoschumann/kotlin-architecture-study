/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.actormodel.counter

data class IncreaseCounterAction(val amount: Int = 1)
data class DecreaseCounterAction(val amount: Int = 1)

data class CounterUpdatedEvent(val newValue: Int)
