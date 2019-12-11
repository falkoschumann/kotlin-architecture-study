/*
 * Architecture Study - Actor Model
 * Copyright (c) 2019 Falko Schumann
 */

java {
    registerFeature("junitSupport") {
        usingSourceSet(sourceSets["main"])
    }
}

dependencies {
    "junitSupportImplementation"("org.junit.jupiter:junit-jupiter:5.5.2")
}
