/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux.counter

import de.muspellheim.shared.DispatchQueue
import javafx.scene.control.Button
import javafx.scene.control.Label
import javax.inject.Inject

/** A supervising controller. */
class CounterReduceViewController @Inject constructor(private val counterStore: CounterReduceStore) {

    lateinit var decreaseButton: Button
    lateinit var valueLabel: Label

    fun initialize() {
        counterStore.addListener { updateState() }
        updateState()
    }

    fun increase() {
        counterStore.dispatcher.dispatch(IncreaseCounterAction())
    }

    fun decrease() {
        counterStore.dispatcher.dispatch(DecreaseCounterAction())
    }

    private fun updateState() {
        DispatchQueue.application {
            valueLabel.text = counterStore.state.value.toString()
            decreaseButton.isDisable = counterStore.state.isDecreaseDisabled
        }
    }
}
