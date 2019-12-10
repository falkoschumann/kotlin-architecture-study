/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

import de.muspellheim.eventbus.EventBus
import de.muspellheim.eventbus.registerActor
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class App(val eventBus: EventBus = EventBus.default()) : Application() {

    internal lateinit var counterActor: CounterActor

    override fun init() {
        counterActor = CounterActor()
        eventBus.registerActor(counterActor)
    }

    override fun start(primaryStage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("/views/CounterView.fxml"))
        val root = loader.load<Parent>()
        val counterViewController = loader.getController<CounterViewController>()
        eventBus.registerActor(counterViewController)

        primaryStage.scene = Scene(root)
        primaryStage.title = "Counter - Actor Model"
        primaryStage.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}
