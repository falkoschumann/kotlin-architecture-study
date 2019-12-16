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

    internal lateinit var counterViewController: CounterViewController
    private lateinit var injector: Injector

    override fun init() {
        injector = Guice.createInjector()
    }

    override fun start(primaryStage: Stage) {
        val root = createRoot()
        primaryStage.scene = Scene(root)
        primaryStage.title = "Counter - MVVM"
        primaryStage.show()
    }

    fun createRoot(): Parent {
        val loader = FXMLLoader(javaClass.getResource("/views/CounterView.fxml"))
        loader.controllerFactory = Callback { injector.getInstance(it) }
        val view = loader.load<Parent>()
        counterViewController = loader.getController<CounterViewController>()
        return view
    }
}

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}
