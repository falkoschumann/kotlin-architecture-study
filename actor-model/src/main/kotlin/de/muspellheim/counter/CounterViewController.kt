/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

import de.muspellheim.eventbus.JavaFxActor
import javafx.beans.property.ReadOnlyBooleanWrapper
import javafx.beans.property.ReadOnlyStringProperty
import javafx.beans.property.ReadOnlyStringWrapper

/** A supervising controller. */
class CounterViewController : JavaFxActor() {

    private val valueProperty by lazy { ReadOnlyStringWrapper(this, "value", "0") }
    fun valueProperty(): ReadOnlyStringProperty = valueProperty.readOnlyProperty
    var value: String
        get() = valueProperty.get()
        private set(value) = valueProperty.set(value)

    private val descreaseDisableProperty by lazy { ReadOnlyBooleanWrapper(this, "descreaseDisable", true) }
    fun descreaseDisableProperty() = descreaseDisableProperty.readOnlyProperty!!
    var descreaseDisable: Boolean
        get() = descreaseDisableProperty.get()
        private set(value) = descreaseDisableProperty.set(value)

    fun increase() {
        outbox(IncreaseCounterAction())
    }

    fun decrease() {
        outbox(DecreaseCounterAction())
    }

    override fun work(message: Any) {
        if (message is CounterUpdatedEvent) {
            value = message.newValue.toString()
            descreaseDisable = message.newValue <= 0
        }
    }
}
