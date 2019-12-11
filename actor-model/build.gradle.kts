/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

plugins {
    application
}

dependencies {
    implementation(project(":shared"))
}

application {
    mainClassName = "de.muspellheim.counter.AppKt"
}

