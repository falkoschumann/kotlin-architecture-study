/*
 * Architecture Study - Redux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.redux.counter

/** A state. */
data class Counter(val value: Int, val isDecreaseDisabled: Boolean)
