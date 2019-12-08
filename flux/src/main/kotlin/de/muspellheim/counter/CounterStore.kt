/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

import de.muspellheim.flux.Dispatcher
import de.muspellheim.flux.Store
import de.muspellheim.shared.DispatchQueue
import java.lang.Integer.max
import javafx.beans.property.ReadOnlyIntegerWrapper

/** A simple store. */
class CounterStore(dispatcher: Dispatcher<CounterAction>) : Store<CounterAction>(dispatcher) {

    private val valueProperty by lazy { ReadOnlyIntegerWrapper(this, "value", 0) }
    fun valueProperty() = valueProperty.readOnlyProperty!!
    var value: Int
        get() = valueProperty.get()
        private set(value) = valueProperty.set(value)

    override fun onDispatch(payload: CounterAction) {
        when (payload) {
            is IncreaseCounterAction -> increase()
            is DecreaseCounterAction -> decrease()
        }
    }

    private fun increase() {
        DispatchQueue.background {
            val v = value + 1
            DispatchQueue.application {
                value = v
            }
        }
    }

    private fun decrease() {
        DispatchQueue.background {
            val v = max(0, value - 1)
            DispatchQueue.application {
                value = v
            }
        }
    }
}
