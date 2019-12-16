/*
 * Architecture Study - Redux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.redux.counter

import de.muspellheim.redux.Store
import de.muspellheim.shared.DispatchQueue
import javafx.scene.control.Button
import javafx.scene.control.Label
import javax.inject.Inject

/** A supervising controller. */
class CounterViewController @Inject constructor(private val counterStore: Store<Counter>) {

    lateinit var decreaseButton: Button
    lateinit var valueLabel: Label

    fun initialize() {
        counterStore.subscribe { update() }
        update()
    }

    fun increase() {
        counterStore.dispatch(IncreaseCounterAction())
    }

    fun decrease() {
        counterStore.dispatch(DecreaseCounterAction())
    }

    private fun update() {
        DispatchQueue.application {
            valueLabel.text = counterStore.state.value.toString()
            decreaseButton.isDisable = counterStore.state.isDecreaseDisabled
        }
    }
}
