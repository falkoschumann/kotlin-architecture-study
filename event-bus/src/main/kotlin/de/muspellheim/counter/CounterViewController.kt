/*
 * Architecture Study - Event Bus
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

import javafx.application.Platform
import javafx.beans.property.ReadOnlyStringProperty
import javafx.beans.property.ReadOnlyStringWrapper

/** View model. */
class CounterViewController {

    private val valueProperty by lazy { ReadOnlyStringWrapper(this, "value", "0") }
    fun valueProperty(): ReadOnlyStringProperty = valueProperty.readOnlyProperty
    var value: String
        get() = valueProperty.get()
        private set(value) = valueProperty.set(value)

    init {
        EventBus.default().subscribe { handleEvent(it) }
    }

    fun increase() {
        EventBus.default().publish(IncreaseCounterAction())
    }

    fun decrease() {
        EventBus.default().publish(DecreaseCounterAction())
    }

    private fun handleEvent(event: Any) {
        if (event is CounterUpdatedEvent) {
            Platform.runLater { value = event.newValue.toString() }
        }
    }
}
