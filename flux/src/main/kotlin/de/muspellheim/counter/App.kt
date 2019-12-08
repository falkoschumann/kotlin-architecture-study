/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

import de.muspellheim.flux.Dispatcher
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

/** The app builds and binds the components and services. */
class App : Application() {

    internal lateinit var dispatcher: Dispatcher<CounterAction>
    internal lateinit var counterStore: CounterStore

    override fun init() {
        dispatcher = Dispatcher()
        counterStore = CounterStore(dispatcher)
    }

    override fun start(primaryStage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("/views/CounterView.fxml"))
        val root = loader.load<Parent>()
        val counterViewController = loader.getController<CounterViewController>()
        counterViewController.injectCounterStore(counterStore)

        primaryStage.scene = Scene(root)
        primaryStage.title = "Counter - Flux"
        primaryStage.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}
