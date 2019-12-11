/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.actormodel

/** Asynchronously Event Bus. */
class EventBus(val name: String) {

    private var subscribers: List<(Any) -> Unit> = listOf()

    fun subscribe(subscriber: (Any) -> Unit) {
        subscribers = subscribers + subscriber
    }

    fun unsubscribe(subscriber: (Any) -> Unit) {
        subscribers = subscribers - subscriber
    }

    fun publish(event: Any) {
        subscribers.forEach {
            it(event)
        }
    }

    companion object {
        private val default = EventBus("Default Event Bus")
        fun default() = default
    }
}
