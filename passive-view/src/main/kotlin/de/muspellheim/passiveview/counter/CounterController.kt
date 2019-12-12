/*
 * Architecture Study - Passive View
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.passiveview.counter

import de.muspellheim.shared.DispatchQueue

/** A controller for model and view. */
class CounterController(val model: CounterService, val view: CounterView) {

    init {
        view.onIncrease += { increase() }
        view.onDecrease += { decrease() }
        updateState()
    }

    fun increase() {
        DispatchQueue.background {
            model.increment()
            DispatchQueue.application {
                updateState()
            }
        }
    }

    fun decrease() {
        DispatchQueue.background {
            model.decrement()
            DispatchQueue.application {
                updateState()
            }
        }
    }

    private fun updateState() {
        view.value = model.value.toString()
        view.isDescreaseDisable = model.value <= 0
    }
}
