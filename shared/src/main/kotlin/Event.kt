/*
 * Architecture Study - Shared
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.shared

class Event<T> {

    private var listeners = listOf<(T) -> Unit>()

    operator fun plusAssign(listener: (T) -> Unit) {
        listeners = listeners + listener
    }

    operator fun minusAssign(listener: (T) -> Unit) {
        listeners = listeners - listener
    }

    operator fun invoke(message: T) {
        listeners.forEach {
            it(message)
        }
    }
}
