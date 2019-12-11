/*
 * Architecture Study - Model View ViewModel
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.mvvm.counter

import de.muspellheim.shared.DispatchQueue
import javafx.beans.property.ReadOnlyBooleanWrapper
import javafx.beans.property.ReadOnlyStringWrapper
import javax.inject.Inject

/** The view model interacts with the user through the user interface. */
class CounterViewController @Inject constructor(private val counterService: CounterService) {

    private val valueProperty by lazy { ReadOnlyStringWrapper(this, "value", "") }
    fun valueProperty() = valueProperty.readOnlyProperty!!
    var value: String
        get() = valueProperty.get()
        private set(value) = valueProperty.set(value)

    private val descreaseDisableProperty by lazy { ReadOnlyBooleanWrapper(this, "descreaseDisable", true) }
    fun descreaseDisableProperty() = descreaseDisableProperty.readOnlyProperty!!
    var descreaseDisable: Boolean
        get() = descreaseDisableProperty.get()
        private set(value) = descreaseDisableProperty.set(value)

    init {
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
        descreaseDisable = counterService.value <= 0
    }
}
