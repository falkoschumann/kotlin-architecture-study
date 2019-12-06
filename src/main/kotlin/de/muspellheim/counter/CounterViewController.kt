/*
 * Architecture Study
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

import javafx.beans.property.ReadOnlyStringProperty
import javafx.beans.property.ReadOnlyStringWrapper

/** View model. */
class CounterViewController {

    private val valueProperty by lazy { ReadOnlyStringWrapper(this, "value", "") }
    fun valueProperty(): ReadOnlyStringProperty = valueProperty.readOnlyProperty
    var value: String
        get() = valueProperty.get()
        private set(value) = valueProperty.set(value)

    private lateinit var counterService: CounterService

    fun injectCounterService(service: CounterService) {
        counterService = service
        updateValue()
    }

    fun increase() {
        DispatchQueue.background.execute {
            counterService.increment()
            DispatchQueue.application.execute {
                updateValue()
            }
        }
    }

    fun decrease() {
        DispatchQueue.background.execute {
            counterService.decrement()
            DispatchQueue.application.execute {
                updateValue()
            }
        }
    }

    private fun updateValue() {
        value = counterService.value.toString()
    }
}
