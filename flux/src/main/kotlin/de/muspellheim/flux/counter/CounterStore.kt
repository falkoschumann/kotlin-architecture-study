/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux.counter

import de.muspellheim.flux.Dispatcher
import de.muspellheim.flux.Store
import javax.inject.Inject
import javax.inject.Singleton

/** A store. */
@Singleton
class CounterStore @Inject constructor(dispatcher: Dispatcher, val manager: CounterManager) : Store(dispatcher) {

    var value = 0
        private set

    var isDecreasable = false
        private set

    override fun onDispatch(payload: Any) {
        when (payload) {
            is IncreaseCounterAction -> {
                manager.increase(value, payload.amount)
                emitChange()
            }
            is DecreaseCounterAction -> {
                manager.decrease(value, payload.amount)
                emitChange()
            }
            is CounterChangedAction -> {
                value = payload.newValue
                isDecreasable = payload.newValue > 0
                emitChange()
            }
        }
    }
}
