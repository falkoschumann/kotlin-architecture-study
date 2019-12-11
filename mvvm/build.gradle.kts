/*
 * Architecture Study - Model View ViewModel
 * Copyright (c) 2019 Falko Schumann
 */

plugins {
    application
}

dependencies {
    implementation(project(":shared"))
}

application {
    mainClassName = "de.muspellheim.mvvm.counter.AppKt"
}
