/*
 * Architecture Study
 * Copyright (c) 2019 Falko Schumann
 */

plugins {
    base
    kotlin("jvm") version "1.3.41" apply false
    id("com.diffplug.gradle.spotless") version "3.26.1"
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.diffplug.gradle.spotless")

    repositories {
        jcenter()
    }

    val implementation by configurations
    val testImplementation by configurations
    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.41")

        testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    spotless {
        kotlin {
            ktlint()
            licenseHeaderFile("config/spotless/license.header")
        }
    }
}
