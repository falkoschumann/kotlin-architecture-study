/*
 * Architecture Study - Redux
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.redux.counter

import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.google.inject.Injector
import de.muspellheim.redux.Store
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.util.Callback

/** The app builds stores and root view. */
class App : Application() {

    internal lateinit var store: Store<Counter>
    private lateinit var injector: Injector

    override fun init() {
        store = createStore()
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
                bind(Store::class.java).toInstance(store)
            }
        }
        return Guice.createInjector(module)
    }

    private fun createRoot(): Parent {
        val loader = FXMLLoader(javaClass.getResource("/views/CounterView.fxml"))
        loader.controllerFactory = Callback { injector.getInstance(it) }
        return loader.load<Parent>()
    }
}

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}
