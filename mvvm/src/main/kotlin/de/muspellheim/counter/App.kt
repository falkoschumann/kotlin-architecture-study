/*
 * Architecture Study - Model View ViewModel
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

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

    internal lateinit var counterService: CounterService

    private lateinit var injector: Injector

    override fun init() {
        counterService = CounterService()

        injector = Guice.createInjector()
    }

    override fun start(primaryStage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("/views/CounterView.fxml"))
        loader.controllerFactory = Callback { injector.getInstance(it) }
        val root = loader.load<Parent>()

        primaryStage.scene = Scene(root)
        primaryStage.title = "Counter - MVVM"
        primaryStage.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}
