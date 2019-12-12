/*
 * Architecture Study - Model View ViewModel
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.mvvm.counter

import com.google.inject.Guice
import com.google.inject.Injector
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.util.Callback

/** The app builds the root view-model. */
class App : Application() {

    private lateinit var injector: Injector

    override fun init() {
        injector = Guice.createInjector()
    }

    override fun start(primaryStage: Stage) {
        val root = createRoot()
        primaryStage.scene = Scene(root.first)
        primaryStage.title = "Counter - MVVM"
        primaryStage.show()
    }

    fun createRoot(): Pair<Parent, CounterViewController> {
        val loader = FXMLLoader(javaClass.getResource("/views/CounterView.fxml"))
        loader.controllerFactory = Callback { injector.getInstance(it) }
        val view = loader.load<Parent>()
        val controller = loader.getController<CounterViewController>()
        return Pair(view, controller)
    }
}

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}
