/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.actormodel

import de.muspellheim.shared.Action
import javafx.application.Platform

abstract class JavaFxActor : Actor {

    override val outbox = Action<Any>()

    override fun receive(message: Any) {
        if (Platform.isFxApplicationThread()) {
            work(message)
        } else {
            Platform.runLater { work(message) }
        }
    }
}
