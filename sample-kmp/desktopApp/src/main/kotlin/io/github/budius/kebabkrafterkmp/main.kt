package io.github.budius.kebabkrafterkmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Kebab Krafter Kmp",
    ) {
        App()
    }
}