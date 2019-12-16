/*
 * Architecture Study - Passive View
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.passiveview.counter

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

/** The app builds and binds the components and services. */
class App : Application() {

    internal lateinit var counterController: CounterController

    private lateinit var counterService: CounterService

    override fun init() {
        counterService = CounterService()
    }

    override fun start(primaryStage: Stage) {
        val root = createRoot()
        primaryStage.scene = Scene(root)
        primaryStage.title = "Counter - Passive View"
        primaryStage.show()
    }

    internal fun createRoot(): Parent {
        val loader = FXMLLoader(javaClass.getResource("/views/CounterView.fxml"))
        val view = loader.load<Parent>()
        val viewController = loader.getController<CounterViewController>()
        counterController = CounterController(counterService, viewController)
        return view
    }
}

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}
