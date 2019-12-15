/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux

import de.muspellheim.shared.Action

/** Base class of a store. */
abstract class Store(val dispatcher: Dispatcher) {

    val dispatchToken: DispatchToken

    private var changed = false

    private var listeners = emptyList<() -> Unit>()

    init {
        dispatchToken = dispatcher.register { invokeOnDispatch(it) }
    }

    fun addListener(listener: () -> Unit): Remover {
        listeners = listeners + listener
        return object : Remover {
            override fun remove() {
                listeners = listeners - listener
            }
        }
    }

    fun hasChanged(): Boolean {
        assert(dispatcher.isDispatching) { "$javaClass.isChanged: Must be invoked while dispatching." }
        return changed
    }

    protected fun emitChange() {
        assert(dispatcher.isDispatching) { "$javaClass.emitChange(): Must be invoked while dispatching." }
        changed = true
    }

    private fun invokeOnDispatch(payload: Any) {
        changed = false
        onDispatch(payload)
        if (changed) {
            listeners.forEach { it() }
        }
    }

    protected abstract fun onDispatch(payload: Any)
}

interface Remover {
    fun remove()
}
