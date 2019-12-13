/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux.counter

import de.muspellheim.flux.Dispatcher
import de.muspellheim.flux.Store
import de.muspellheim.shared.DispatchQueue
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.max

/** A store. */
@Singleton
class CounterStore @Inject constructor(dispatcher: Dispatcher) : Store(dispatcher) {

    // TODO Extract manager for API calls

    var value = 0
        private set

    var isDecreasable = false
        private set

    override fun onDispatch(payload: Any) {
        when (payload) {
            is IncreaseCounterAction -> {
                DispatchQueue.background {
                    TimeUnit.SECONDS.sleep(1)
                    val value = value + payload.amount
                    dispatcher.dispatch(CounterChangedAction(value))
                }
                emitChange()
            }
            is DecreaseCounterAction -> {
                DispatchQueue.background {
                    TimeUnit.SECONDS.sleep(1)
                    val value = max(0, value - payload.amount)
                    dispatcher.dispatch(CounterChangedAction(value))
                }
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