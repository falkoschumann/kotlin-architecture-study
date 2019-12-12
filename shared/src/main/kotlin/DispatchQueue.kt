/*
 * Architecture Study - Shared
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.shared

import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue
import javafx.application.Platform

/** Run tasks in one background queue or in the application queue. */
object DispatchQueue {

    // TODO Replace with coroutine?
    // TODO Kurzschluss durch isTesting wieder einführen

    private val backgroundExecutor = object : Executor {

        private val tasks = LinkedBlockingQueue<Runnable>()

        init {
            val task = Runnable {
                while (!Thread.currentThread().isInterrupted) {
                    try {
                        tasks.take().run()
                    } catch (e: InterruptedException) {
                        Thread.currentThread().interrupt()
                    }
                }
            }
            val thread = Thread(task, "Background Queue Worker")
            thread.isDaemon = true
            thread.start()
        }

        override fun execute(command: Runnable) {
            try {
                tasks.put(command)
            } catch (ignored: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }

    fun application(task: () -> Unit) {
        if (Platform.isFxApplicationThread()) {
            task()
        } else {
            Platform.runLater(task)
        }
    }

    fun background(task: () -> Unit) {
        backgroundExecutor.execute(task)
    }
}
