/*
 * Architecture Study - Model View ViewModel
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.mvvm.counter

import de.muspellheim.shared.DispatchQueue
import javafx.beans.property.ReadOnlyBooleanWrapper
import javafx.beans.property.ReadOnlyStringWrapper
import javax.inject.Inject

/** A view-model. */
class CounterViewController @Inject constructor(private val counterService: CounterService) {

    private val valueProperty by lazy { ReadOnlyStringWrapper(this, "value", "") }
    fun valueProperty() = valueProperty.readOnlyProperty!!
    var value: String
        get() = valueProperty.get()
        private set(value) = valueProperty.set(value)

    private val descreaseDisabledProperty by lazy { ReadOnlyBooleanWrapper(this, "descreaseDisabled", true) }
    fun descreaseDisabledProperty() = descreaseDisabledProperty.readOnlyProperty!!
    var isDescreaseDisabled: Boolean
        get() = descreaseDisabledProperty.get()
        private set(value) = descreaseDisabledProperty.set(value)

    init {
        updateState()
    }

    fun increase() {
        DispatchQueue.background {
            counterService.increase()
            DispatchQueue.application {
                updateState()
            }
        }
    }

    fun decrease() {
        DispatchQueue.background {
            counterService.decrease()
            DispatchQueue.application {
                updateState()
            }
        }
    }

    private fun updateState() {
        value = counterService.value.toString()
        isDescreaseDisabled = counterService.value <= 0
    }
}
