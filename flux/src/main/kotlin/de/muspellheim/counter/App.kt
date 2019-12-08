/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import de.muspellheim.flux.Dispatcher
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.util.Callback

/** The app builds and binds the components and services. */
class App : Application() {

    internal lateinit var dispatcher: Dispatcher<Any>
    internal lateinit var counterStore: CounterStore
    internal lateinit var counterActions: CounterActions

    private lateinit var injector: Injector

    override fun init() {
        dispatcher = Dispatcher()
        counterStore = CounterStore(dispatcher)
        counterActions = CounterActions(dispatcher)

        val module = object : AbstractModule() {
            override fun configure() {
                bind(Dispatcher::class.java) to dispatcher
                bind(CounterStore::class.java) to counterStore
                bind(CounterActions::class.java) to counterActions
            }
        }
        injector = Guice.createInjector(module)
    }

    override fun start(primaryStage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("/views/CounterView.fxml"))
        loader.controllerFactory = Callback { injector.getInstance(it) }
        val root = loader.load<Parent>()

        primaryStage.scene = Scene(root)
        primaryStage.title = "Counter - Flux"
        primaryStage.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}
