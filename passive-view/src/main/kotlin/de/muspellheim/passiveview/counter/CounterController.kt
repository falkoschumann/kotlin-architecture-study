/*
 * Architecture Study - Model View ViewModel
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.passiveview.counter

import de.muspellheim.shared.DispatchQueue

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
        view.descreaseDisable = model.value <= 0
    }
}
