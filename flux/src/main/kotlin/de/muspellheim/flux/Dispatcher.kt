/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux

typealias DispatchToken = String
typealias Callback = (payload: Any) -> Unit

private const val PREFIX = "ID_"

/** Class of the central dispatcher. */
class Dispatcher {

    // TODO waitFor(ids: List<String)

    private var _isDispatching = false
    val isDispatching: Boolean get() = _isDispatching

    private var lastId = 1
    private var callbacks = mapOf<DispatchToken, Callback>()

    fun register(callback: Callback): DispatchToken {
        val id = PREFIX + lastId++
        callbacks = callbacks + Pair(id, callback)
        return id
    }

    fun unregister(id: DispatchToken) {
        assert(callbacks.containsKey(id)) { "Dispatcher.unregister(...): $id does not map to a registered callback." }
        callbacks = callbacks - id
    }

    fun dispatch(payload: Any) {
        assert(!isDispatching) { "Dispatch.dispatch(...): Cannot dispatch in the middle of a dispatch." }
        _isDispatching = true
        try {
            callbacks.forEach {
                it.value(payload)
            }
        } finally {
            _isDispatching = false
        }
    }
}
