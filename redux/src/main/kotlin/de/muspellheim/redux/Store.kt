/*
 * Architecture Study - Redux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.redux

class Store<S, A>(private val reducer: Reducer<S, A>, initialState: S) {

    var state: S = initialState
        private set

    private var listeners = listOf<() -> Unit>()

    fun dispatch(action: A) {
        val startingState = state
        val endingState = reducer(startingState, action)
        if (startingState != endingState) {
            state = endingState
        }
    }

    fun subscribe(listener: () -> Unit): Unsubscribe {
        listeners = listeners + listener
        return { listeners = listeners - listener }
    }
}

typealias Reducer<S, A> = (state: S, action: A) -> S
typealias Unsubscribe = () -> Unit
