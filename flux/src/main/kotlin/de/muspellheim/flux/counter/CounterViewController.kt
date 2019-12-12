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
class CounterViewController @Inject constructor(
    private val counterStore: CounterStore,
    private val counterActions: CounterActions
) {

    lateinit var decreaseButton: Button
    lateinit var valueLabel: Label

    fun initialize() {
        counterStore.onChanged += { update() }
        update()
    }

    fun increase() {
        counterActions.increase()
    }

    fun decrease() {
        counterActions.decrease()
    }

    private fun update() {
        DispatchQueue.application {
            valueLabel.text = counterStore.state.value.toString()
            decreaseButton.isDisable = !counterStore.state.isDecreasable
        }
    }
}
