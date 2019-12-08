/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

import javafx.beans.property.ReadOnlyStringProperty
import javafx.beans.property.ReadOnlyStringWrapper

/** A view controller. */
class CounterViewController {

    private val valueProperty by lazy { ReadOnlyStringWrapper(this, "value", "") }
    fun valueProperty(): ReadOnlyStringProperty = valueProperty.readOnlyProperty
    var value: String
        get() = valueProperty.get()
        private set(value) = valueProperty.set(value)

    private lateinit var counterStore: CounterStore

    fun injectCounterStore(store: CounterStore) {
        counterStore = store
        valueProperty.bind(counterStore.valueProperty().asString())
    }

    fun increase() {
        counterStore.dispatcher.dispatch(IncreaseCounterAction())
    }

    fun decrease() {
        counterStore.dispatcher.dispatch(DecreaseCounterAction())
    }
}
