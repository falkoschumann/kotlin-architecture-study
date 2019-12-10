/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.eventbus

class Event<T> {

    private var handlers = listOf<(T) -> Unit>()

    fun addHandler(handler: (T) -> Unit) {
        handlers = handlers + handler
    }

    fun removeHandler(handler: (T) -> Unit) {
        handlers = handlers - handler
    }

    fun send(message: T) {
        handlers.forEach {
            try {
                it(message)
            } catch (e: Exception) {
                Thread.currentThread().uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), e)
            }
        }
    }
}