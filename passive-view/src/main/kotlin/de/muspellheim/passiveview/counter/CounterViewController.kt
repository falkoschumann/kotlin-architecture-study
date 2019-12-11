/*
 * Architecture Study - Passive View
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.passiveview.counter

import de.muspellheim.shared.Action
import javafx.scene.control.Button
import javafx.scene.control.Label

/** A passive view. */
class CounterViewController : CounterView {

    override var value: String
        get() = valueLabel.text
        set(value) {
            valueLabel.text = value
        }

    override var descreaseDisable
        get() = decreaseButton.isDisabled
        set(value) {
            decreaseButton.isDisable = value
        }

    override val onIncrease = Action<Void>()
    override val onDecrease = Action<Void>()

    lateinit var decreaseButton: Button
    lateinit var valueLabel: Label

    fun increase() {
        onIncrease()
    }

    fun decrease() {
        onDecrease()
    }
}
