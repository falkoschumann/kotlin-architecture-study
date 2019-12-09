/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux

import de.muspellheim.shared.EmitterSubscription
import de.muspellheim.shared.EventEmitter

/** Base class of a store. */
abstract class Store(val dispatcher: Dispatcher<Any>) {

    val dispatchToken: DispatchToken

    var isChanged = false
        get() {
            assert(dispatcher.isDispatching) { "$javaClass.isChanged: Must be invoked while dispatching." }
            return field
        }
        protected set

    protected val changed = EventEmitter<Unit>()

    init {
        dispatchToken = dispatcher.register { invokeOnDispatch(it) }
    }

    fun addListener(callback: (Unit?) -> Unit): EmitterSubscription {
        return changed.addListener(callback)
    }

    protected fun emitChange() {
        assert(dispatcher.isDispatching) { "$javaClass.emitChange(): Must be invoked while dispatching." }
        isChanged = true
    }

    protected open fun invokeOnDispatch(payload: Any) {
        isChanged = false
        onDispatch(payload)
        if (isChanged) {
            changed.emit()
        }
    }

    protected fun onDispatch(payload: Any) {
        assert(false) { "$javaClass has not overridden Store.onDispatch(), which is required" }
    }
}
