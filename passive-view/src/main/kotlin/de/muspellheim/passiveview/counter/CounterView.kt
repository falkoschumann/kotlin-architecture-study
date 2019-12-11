/*
 * Architecture Study - Passive View
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.passiveview.counter

import de.muspellheim.shared.Action

interface CounterView {

    var value: String
    var descreaseDisable: Boolean

    val onIncrease: Action<Void>
    val onDecrease: Action<Void>
}
