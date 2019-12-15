/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.actormodel

import javafx.application.Platform

abstract class JavaFxActor : Actor() {

    override fun receive(message: Any) {
        if (Platform.isFxApplicationThread()) {
            work(message)
        } else {
            Platform.runLater { work(message) }
        }
    }
}
