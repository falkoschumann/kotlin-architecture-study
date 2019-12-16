/*
 * Architecture Study - Presentation Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.presentationmodel.counter

import de.muspellheim.shared.Action
import javax.inject.Inject

/** A presentation model. */
class CounterModel @Inject constructor(private val model: CounterService) {

    val value: String
        get() = model.value.toString()

    val isDescreaseDisable: Boolean
        get() = model.value <= 0

    val onIncreased = Action()
    val onDecreased = Action()

    fun increase() {
        model.increase()
        onIncreased()
    }

    fun decrease() {
        model.decrease()
        onDecreased()
    }
}
