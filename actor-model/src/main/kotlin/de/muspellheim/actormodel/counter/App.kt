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

/** The app builds and connects the actors. */
class App(private val eventBus: EventBus = EventBus.default()) : Application() {

    override fun init() {
        val counter = Counter()
        val counterActor = CounterActor(counter)
        eventBus.registerActor(counterActor)
    }

    override fun start(primaryStage: Stage) {
        val root = createRoot()
        primaryStage.scene = Scene(root.first)
        primaryStage.title = "Counter - Actor Model"
        primaryStage.show()
    }

    internal fun createRoot(): Pair<Parent, CounterViewController> {
        val loader = FXMLLoader(javaClass.getResource("/views/CounterView.fxml"))
        val view = loader.load<Parent>()
        val controller = loader.getController<CounterViewController>()
        eventBus.registerActor(controller)
        return Pair(view, controller)
    }
}

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}
