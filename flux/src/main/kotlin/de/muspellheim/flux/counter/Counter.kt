/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux.counter

/** A state. */
data class Counter(val value: Int, val isDecreaseDisabled: Boolean)
