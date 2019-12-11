/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.actormodel.counter

import de.muspellheim.actormodel.EventBus
import de.muspellheim.actormodel.registerActor
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class App(private val eventBus: EventBus = EventBus.default()) : Application() {

    override fun init() {
        val counter = Counter()
        val counterActor = CounterActor(counter)
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
