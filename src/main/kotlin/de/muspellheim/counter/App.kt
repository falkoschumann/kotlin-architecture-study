/*
 * Architecture Study
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.counter

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class App : Application() {

    private lateinit var counterService: CounterService

    override fun init() {
        counterService = CounterService()
    }

    override fun start(primaryStage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("/views/CounterView.fxml"))
        val root = loader.load<Parent>()
        val controller = loader.getController<CounterViewController>()
        controller.injectCounterService(CounterService())

        primaryStage.scene = Scene(root)
        primaryStage.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}
