/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux

import de.muspellheim.shared.Action

/** Base class of a store. */
abstract class Store(val dispatcher: Dispatcher) {

    val dispatchToken: DispatchToken

    var isChanged = false
        get() {
            assert(dispatcher.isDispatching) { "$javaClass.isChanged: Must be invoked while dispatching." }
            return field
        }
        private set

    val onChanged = Action<Unit>()

    init {
        dispatchToken = dispatcher.register { invokeOnDispatch(it) }
    }

    protected fun emitChange() {
        assert(dispatcher.isDispatching) { "$javaClass.emitChange(): Must be invoked while dispatching." }
        isChanged = true
    }

    private fun invokeOnDispatch(payload: Any) {
        isChanged = false
        onDispatch(payload)
        if (isChanged) {
            onChanged()
        }
    }

    protected abstract fun onDispatch(payload: Any)
}
