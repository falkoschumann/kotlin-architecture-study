/*
 * Architecture Study - Presentation Model
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.presentationmodel.counter

import com.google.inject.Guice
import com.google.inject.Injector
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.util.Callback

/** The app builds and binds the components and services. */
class App : Application() {

    lateinit var injector: Injector

    override fun init() {
        injector = createInjector()
    }

    private fun createInjector(): Injector {
        return Guice.createInjector()
    }

    override fun start(primaryStage: Stage) {
        val root = createRoot()
        primaryStage.scene = Scene(root)
        primaryStage.title = "Counter - Presentation Model"
        primaryStage.show()
    }

    fun createRoot(): Parent {
        val loader = FXMLLoader(javaClass.getResource("/views/CounterView.fxml"))
        loader.controllerFactory = Callback { injector.getInstance(it) }
        return loader.load<Parent>()
    }
}

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}
