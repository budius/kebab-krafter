package io.github.budius.kebabkrafterkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform