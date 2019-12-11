/*
 * Architecture Study - Shared
 * Copyright (c) 2019 Falko Schumann
 */

package de.muspellheim.shared

import javafx.embed.swing.JFXPanel
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

class JavaFxExtension : BeforeAllCallback {
    override fun beforeAll(context: ExtensionContext?) {
        JFXPanel()
    }
}
