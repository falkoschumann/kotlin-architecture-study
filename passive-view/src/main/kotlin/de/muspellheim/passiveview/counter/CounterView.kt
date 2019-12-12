/*
 * Architecture Study - Passive View
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.passiveview.counter

import de.muspellheim.shared.Action

/** Optional interface for passive view. */
interface CounterView {

    var value: String
    var isDescreaseDisable: Boolean

    val onIncrease: Action<Void>
    val onDecrease: Action<Void>
}
