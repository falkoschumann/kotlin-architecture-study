/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.actormodel.counter

import de.muspellheim.actormodel.Actor
import de.muspellheim.actormodel.EventBus
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

/** The app creates and connects the actors. */
class App(internal val eventBus: EventBus = EventBus.default()) : Application() {

    internal lateinit var counterActor: CounterActor
    internal lateinit var counterViewActor: CounterViewActor

    override fun init() {
        val counter = Counter()
        counterActor = CounterActor(counter)
        registerActor(eventBus, counterActor)
    }

    override fun start(primaryStage: Stage) {
        val root = createRoot()
        primaryStage.scene = Scene(root)
        primaryStage.title = "Counter - Actor Model"
        primaryStage.show()
    }

    internal fun createRoot(): Parent {
        val loader = FXMLLoader(javaClass.getResource("/views/CounterView.fxml"))
        val view = loader.load<Parent>()
        counterViewActor = loader.getController<CounterViewActor>()
        registerActor(eventBus, counterViewActor)
        return view
    }
}

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}

private fun registerActor(eventBus: EventBus, actor: Actor) {
    eventBus.subscribe {
        actor.receive(it)
    }
    actor.outbox += {
        eventBus.publish(it)
    }
}
