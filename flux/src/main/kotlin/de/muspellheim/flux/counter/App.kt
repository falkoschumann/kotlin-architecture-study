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

    internal lateinit var dispatcher: Dispatcher
    internal lateinit var counterViewController: CounterReduceViewController

    internal lateinit var injector: Injector

    override fun init() {
        dispatcher = Dispatcher()
        injector = createInjector()
    }

    override fun start(primaryStage: Stage) {
        val root = createRoot()
        primaryStage.scene = Scene(root)
        primaryStage.title = "Counter - Flux"
        primaryStage.show()
    }

    private fun createInjector(): Injector {
        val module = object : AbstractModule() {
            override fun configure() {
                bind(Dispatcher::class.java).toInstance(dispatcher)
            }
        }
        return Guice.createInjector(module)
    }

    internal fun createRoot(): Parent {
        val loader = FXMLLoader(javaClass.getResource("/views/CounterView.fxml"))
        loader.controllerFactory = Callback { injector.getInstance(it) }
        val view = loader.load<Parent>()
        counterViewController = loader.getController<CounterReduceViewController>()
        return view
    }
}

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}
