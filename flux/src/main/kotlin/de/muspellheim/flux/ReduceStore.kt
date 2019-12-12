/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux

abstract class ReduceStore<T : Any>(val initialState: T, dispatcher: Dispatcher) : Store(dispatcher) {

    var state: T = initialState

    abstract fun reduce(state: T, action: Any): T

    fun areEqual(one: T, two: T): Boolean {
        return one === two
    }

    override fun onDispatch(payload: Any) {
        val startingState = state
        val endingState = reduce(startingState, payload)
        if (!areEqual(startingState, endingState)) {
            state = endingState
            emitChange()
        }
    }
}
