/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.actormodel.counter

import de.muspellheim.actormodel.JavaFxActor
import javafx.scene.control.Button
import javafx.scene.control.Label

/** An actor as passive view. */
class CounterViewActor : JavaFxActor() {

    lateinit var decreaseButton: Button
    lateinit var valueLabel: Label

    fun increase() {
        outbox(IncreaseCounterAction())
    }

    fun decrease() {
        outbox(DecreaseCounterAction())
    }

    override fun work(message: Any) {
        if (message is CounterUpdatedEvent) {
            valueLabel.text = message.value.toString()
            decreaseButton.isDisable = message.value == 0
        }
    }
}
