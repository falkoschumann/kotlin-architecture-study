/*
 * Architecture Study - Flux
 * Copyright (c) 2019 Falko Schumann
 */

plugins {
    application
}

dependencies {
    implementation(project(":shared"))
}

application {
    mainClassName = "de.muspellheim.flux.counter.AppKt"
}
