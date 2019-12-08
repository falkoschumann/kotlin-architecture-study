/*
 * Architecture Study - Model View ViewModel
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

import de.muspellheim.shared.DispatchQueue
import javafx.beans.property.ReadOnlyStringProperty
import javafx.beans.property.ReadOnlyStringWrapper

/** The view model interacts with the user through the user interface. */
class CounterViewController {

    private val valueProperty by lazy { ReadOnlyStringWrapper(this, "value", "") }
    fun valueProperty(): ReadOnlyStringProperty = valueProperty.readOnlyProperty
    var value: String
        get() = valueProperty.get()
        private set(value) = valueProperty.set(value)

    private lateinit var counterService: CounterService

    fun injectCounterService(service: CounterService) {
        counterService = service
        updateState()
    }

    fun increase() {
        DispatchQueue.background {
            counterService.increment()
            DispatchQueue.application {
                updateState()
            }
        }
    }

    fun decrease() {
        DispatchQueue.background {
            counterService.decrement()
            DispatchQueue.application {
                updateState()
            }
        }
    }

    private fun updateState() {
        value = counterService.value.toString()
    }
}
