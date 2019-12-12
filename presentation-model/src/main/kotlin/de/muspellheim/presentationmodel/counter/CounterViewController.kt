/*
 * Architecture Study - Presentation Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.presentationmodel.counter

import de.muspellheim.shared.DispatchQueue
import javafx.scene.control.Button
import javafx.scene.control.Label
import javax.inject.Inject

/** A supervising controller as view. */
class CounterViewController @Inject constructor(private val model: CounterModel) {

    lateinit var decreaseButton: Button
    lateinit var valueLabel: Label

    fun initialize() {
        model.onIncreased += { updateState() }
        model.onDecreased += { updateState() }
        updateState()
    }

    fun increase() {
        DispatchQueue.background {
            model.increase()
        }
    }

    fun decrease() {
        DispatchQueue.background {
            model.decrease()
        }
    }

    private fun updateState() {
        DispatchQueue.application {
            valueLabel.text = model.value
            decreaseButton.isDisable = model.isDescreaseDisable
        }
    }
}
