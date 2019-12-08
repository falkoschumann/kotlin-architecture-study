/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter.flux

interface CounterAction

data class IncreaseCounterAction(val amount: Int = 1) : CounterAction
data class DecreaseCounterAction(val amount: Int = 1) : CounterAction
