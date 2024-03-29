/*
 * Architecture Study - Shared
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.shared

class Action {

    private var listeners = listOf<() -> Unit>()

    operator fun plusAssign(listener: () -> Unit) {
        listeners = listeners + listener
    }

    operator fun minusAssign(listener: () -> Unit) {
        listeners = listeners - listener
    }

    operator fun invoke() {
        listeners.forEach {
            it()
        }
    }
}
