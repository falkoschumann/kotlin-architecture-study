/*
 * Architecture Study - Event Bus
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter.eventbus

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class App(val eventBus: EventBus = EventBus.default()) : Application() {

    internal lateinit var counterActor: CounterActor

    fun register(actor: Actor) {
        eventBus.subscribe { actor.receive(it) }
        actor.outbox.addHandler { eventBus.publish(it) }
    }

    override fun init() {
        counterActor = CounterActor()
        register(counterActor)
    }

    override fun start(primaryStage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("/views/CounterView.fxml"))
        val root = loader.load<Parent>()
        val counterViewController = loader.getController<CounterViewController>()
        register(counterViewController)

        primaryStage.scene = Scene(root)
        primaryStage.title = "Counter - Event Bus"
        primaryStage.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}
