/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.shared

class EventEmitter<T> {

    private var listeners = listOf<(T?) -> Unit>()

    fun addListener(listener: (T?) -> Unit): EmitterSubscription {
        listeners = listeners + listener
        return object : EmitterSubscription {
            override fun remove() {
                listeners = listeners - listener
            }
        }
    }

    fun emit(message: T? = null) {
        listeners.forEach {
            try {
                it(message)
            } catch (e: Exception) {
                Thread.currentThread().uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), e)
            }
        }
    }
}

interface EmitterSubscription {
    fun remove()
}
