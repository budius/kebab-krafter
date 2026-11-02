package io.github.budius.kebabkrafterkmp

class Greeting {
    private val platform = getPlatform()

    fun greet(): String = sayHello(platform.name)
}
