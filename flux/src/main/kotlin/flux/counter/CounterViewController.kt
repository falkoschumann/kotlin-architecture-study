/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux.counter

import de.muspellheim.shared.DispatchQueue
import javafx.beans.property.ReadOnlyBooleanWrapper
import javafx.beans.property.ReadOnlyStringProperty
import javafx.beans.property.ReadOnlyStringWrapper
import javax.inject.Inject

/** A view controller. */
class CounterViewController @Inject constructor(
    private val counterStore: CounterStore,
    private val counterActions: CounterActions
) {

    private val valueProperty by lazy { ReadOnlyStringWrapper(this, "value", "") }
    fun valueProperty(): ReadOnlyStringProperty = valueProperty.readOnlyProperty
    var value: String
        get() = valueProperty.get()
        private set(value) = valueProperty.set(value)

    private val descreaseDisableProperty by lazy { ReadOnlyBooleanWrapper(this, "descreaseDisable", true) }
    fun descreaseDisableProperty() = descreaseDisableProperty.readOnlyProperty!!
    var descreaseDisable: Boolean
        get() = descreaseDisableProperty.get()
        private set(value) = descreaseDisableProperty.set(value)

    init {
        counterStore.changed += { update() }
        update()
    }

    fun increase() {
        counterActions.increase()
    }

    fun decrease() {
        counterActions.decrease()
    }

    private fun update() {
        DispatchQueue.application {
            value = counterStore.state.value.toString()
            descreaseDisable = !counterStore.state.isDecreasable
        }
    }
}
