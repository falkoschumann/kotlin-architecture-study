/*
 * Architecture Study
 * Copyright (c) $YEAR Falko Schumann
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.3.41"
    id("com.diffplug.gradle.spotless") version "3.26.1"
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
}

application {
    mainClassName = "de.muspellheim.counter.AppKt"
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}

spotless {
    kotlin {
        ktlint()
        licenseHeaderFile("config/spotless/license.header")
    }
}
