/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux

abstract class ReduceStore<S : Any>(val initialState: S, dispatcher: Dispatcher) : Store(dispatcher) {

    var state: S = initialState

    abstract fun reduce(state: S, action: Any): S

    override fun onDispatch(payload: Any) {
        val startingState = state
        val endingState = reduce(startingState, payload)
        if (startingState != endingState) {
            state = endingState
            emitChange()
        }
    }
}
