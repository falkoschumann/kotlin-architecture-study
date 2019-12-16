/*
 * Architecture Study - Model View ViewModel
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.mvvm.counter

import java.lang.Integer.max
import javax.inject.Singleton

/** A simple domain service with state. */
@Singleton
class CounterService {

    private var _value = 0
    val value: Int get() = _value

    fun increase() {
        _value++
    }

    fun decrease() {
        _value = max(0, _value - 1)
    }
}
