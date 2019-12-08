/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

typealias DispatchToken = String
typealias Callback<T> = (payload: T) -> Unit

/** Class of the central dispatcher. */
class Dispatcher<T> {

    var isDispatching = false
        private set

    private val prefix = "ID_"
    private var lastId = 1

    private var callbacks = mapOf<DispatchToken, Callback<T>>()

    fun register(callback: Callback<T>): DispatchToken {
        val id = prefix + lastId++
        callbacks = callbacks + Pair(id, callback)
        return id
    }

    fun unregister(id: DispatchToken) {
        assert(callbacks.containsKey(id)) { "Dispatcher.unregister(...): $id does not map to a registered callback." }
        callbacks = callbacks - id
    }

    fun dispatch(payload: T) {
        assert(!isDispatching) { "Dispatch.dispatch(...): Cannot dispatch in the middle of a dispatch." }
        isDispatching = true
        try {
            callbacks.forEach { it.value(payload) }
        } finally {
            isDispatching = false
        }
    }
}
