/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.flux.counter

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

/** The app builds stores and root view. */
class App : Application() {

    internal lateinit var dispatcher: Dispatcher<Any>
    internal lateinit var counterStore: CounterStore
    internal lateinit var counterActions: CounterActions

    override fun init() {
        dispatcher = Dispatcher()
        counterStore = CounterStore(dispatcher)
        counterActions = CounterActions(dispatcher)
    }

    override fun start(primaryStage: Stage) {
        val injector = createInjector()
        val root = createRoot(injector)

        primaryStage.scene = Scene(root)
        primaryStage.title = "Counter - Flux"
        primaryStage.show()
    }

    private fun createInjector(): Injector {
        val module = object : AbstractModule() {
            override fun configure() {
                bind(Dispatcher::class.java).toInstance(dispatcher)
                bind(CounterStore::class.java).toInstance(counterStore)
                bind(CounterActions::class.java).toInstance(counterActions)
            }
        }
        return Guice.createInjector(module)
    }

    private fun createRoot(injector: Injector): Parent {
        val loader = FXMLLoader(javaClass.getResource("/views/CounterView.fxml"))
        loader.controllerFactory = Callback { injector.getInstance(it) }
        return loader.load()
    }
}

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}
