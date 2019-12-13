/*
 * Architecture Study - Redux
 * Copyright (c) 2019 Falko Schumann
 */

plugins {
    application
}

dependencies {
    implementation(project(":shared"))
}

application {
    mainClassName = "de.muspellheim.redux.counter.AppKt"
}
