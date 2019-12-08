/*
 * Architecture Study - Event Bus
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

interface CounterAction
data class IncreaseCounterAction(val amount: Int = 1) : CounterAction
data class DecreaseCounterAction(val amount: Int = 1) : CounterAction

interface CounterEvent
data class CounterUpdatedEvent(val newValue: Int) : CounterEvent
