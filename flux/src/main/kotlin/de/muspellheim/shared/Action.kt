/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.shared

class Action<T> {

    private var listeners = listOf<(T?) -> Unit>()

    operator fun plusAssign(listener: (T?) -> Unit) {
        listeners = listeners + listener
    }

    operator fun minusAssign(listener: (T?) -> Unit) {
        listeners = listeners - listener
    }

    operator fun invoke(message: T? = null) {
        listeners.forEach { it(message) }
    }
}
