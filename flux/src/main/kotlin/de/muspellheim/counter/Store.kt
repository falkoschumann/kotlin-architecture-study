/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

/** Base class of a store. */
abstract class Store<T>(val dispatcher: Dispatcher<T>) {

    val dispatchToken: DispatchToken

    init {
        dispatchToken = dispatcher.register { onDispatch(it) }
    }

    abstract fun onDispatch(payload: T)
}
