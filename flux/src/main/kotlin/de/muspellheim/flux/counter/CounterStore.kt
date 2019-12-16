/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux.counter

import de.muspellheim.flux.Dispatcher
import de.muspellheim.flux.Store
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.max

// TODO handle actions async

/** A store. */
@Singleton
class CounterStore @Inject constructor(dispatcher: Dispatcher) : Store(dispatcher) {

    private var _value = 0
    val value: Int get() = _value

    private var _isDecreaseDisabled = true
    val isDecreaseDisabled: Boolean get() = _isDecreaseDisabled

    override fun onDispatch(payload: Any) {
        when (payload) {
            is IncreaseCounterAction -> {
                _value++
                _isDecreaseDisabled = false
                emitChange()
            }
            is DecreaseCounterAction -> {
                _value = max(0, _value - 1)
                _isDecreaseDisabled = value == 0
                emitChange()
            }
        }
    }
}
