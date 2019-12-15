/*
 * Architecture Study - Redux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.redux

class Store<S>(private val currentReducer: Reducer<S>, preloadedState: S) {

    // TODO Middleware

    private var currentState = preloadedState
    val state: S
        get() {
            assert(!isDispatching) {
                "You may not call store.getState() while the reducer is executing. " +
                    "The reducer has already received the state as an argument. " +
                    "Pass it down from the top reducer instead of reading it from the store."
            }
            return currentState
        }

    private var listeners = emptyList<() -> Unit>()

    private var isDispatching = false

    init {
        dispatch(InitAction())
    }

    fun dispatch(action: Any) {
        assert(!isDispatching) { "Reducers may not dispatch actions." }
        try {
            isDispatching = true
            currentState = currentReducer(currentState, action)
        } finally {
            isDispatching = false
        }
    }

    fun subscribe(listener: () -> Unit): Unsubscribe {
        assert(!isDispatching) {
            "You may not call store.subscribe() while the reducer is executing. " +
                "If you would like to be notified after the store has been updated, subscribe from a " +
                "component and invoke store.getState() in the callback to access the latest state. "
        }
        listeners = listeners + listener

        var isSubscribed = true
        return {
            if (isSubscribed) {
                assert(!isDispatching) {
                    "You may not unsubscribe from a store listener while the reducer is executing."
                }
                isSubscribed = false
                listeners = listeners - listener
            }
        }
    }
}

typealias Reducer<S> = (state: S, action: Any) -> S
typealias Unsubscribe = () -> Unit

class InitAction

fun <S> combineReducers(vararg reducers: Reducer<S>): Reducer<S> = { state, action ->
    reducers.fold(state, { s, reducer -> reducer(s, action) })
}
