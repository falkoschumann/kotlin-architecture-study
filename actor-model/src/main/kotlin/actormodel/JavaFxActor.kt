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
        when {
            isTesting -> work(message)
            Platform.isFxApplicationThread() -> work(message)
            else -> Platform.runLater { doWork(message) }
        }
    }

    private fun doWork(message: Any) {
        try {
            work(message)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private fun handleException(e: Exception) {
        Thread.currentThread()
            .uncaughtExceptionHandler
            .uncaughtException(Thread.currentThread(), e)
    }

    companion object {
        var isTesting = false
    }
}
