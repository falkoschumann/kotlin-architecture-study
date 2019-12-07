/*
 * Architecture Study MVVM
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue
import javafx.application.Platform

object DispatchQueue {

    private val applicationExecutor = Executor {
        if (Platform.isFxApplicationThread()) {
            it.run()
        } else {
            Platform.runLater(it)
        }
    }

    private val backgroundExecutor = object : Executor {

        private val tasks = LinkedBlockingQueue<Runnable>()

        init {
            val task = Runnable {
                while (!Thread.currentThread().isInterrupted) {
                    try {
                        tasks.take().run()
                    } catch (e: InterruptedException) {
                        Thread.currentThread().interrupt()
                    } catch (e: Exception) {
                        Thread.currentThread()
                            .uncaughtExceptionHandler
                            .uncaughtException(Thread.currentThread(), e)
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

    private val testExecutor = Executor { it.run() }

    var isTesting = false

    val application
        get() = if (isTesting) testExecutor else applicationExecutor

    val background
        get() = if (isTesting) testExecutor else backgroundExecutor
}