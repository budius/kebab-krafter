package com.diconium.mobile.tools.kebabkrafter.plugin.client

import com.diconium.mobile.tools.kebabkrafter.DefaultKtorControllerMapper
import com.diconium.mobile.tools.kebabkrafter.generator.toCamelCase
import com.diconium.mobile.tools.kebabkrafter.generator.toPascalCase
import com.diconium.mobile.tools.kebabkrafter.plugin.DefaultEndpointTransformer
import com.diconium.mobile.tools.kebabkrafter.plugin.DefaultKtorTransformer
import com.diconium.mobile.tools.kebabkrafter.plugin.registerAndroid
import com.diconium.mobile.tools.kebabkrafter.plugin.registerJvm
import com.diconium.mobile.tools.kebabkrafter.plugin.registerKmp
import org.gradle.api.DefaultTask
import org.gradle.api.Project

fun applyGenerateKtorClient(target: Project) {
    // create extension
    val ktorClient = target.extensions.create("ktorClient", KtorClientExtension::class.java)

    // apply defaults
    ktorClient.log.convention(false)

    val baseTask by lazy {
        target.tasks.register("generateKtorClient", DefaultTask::class.java) {
            it.group = "generator"
        }
    }

    ktorClient.services.whenObjectAdded { ktorClientInput ->
        require(ktorClientInput.name.isBlank().not()) { "Service name cannot be empty" }
        val folderName = ktorClientInput.name.toCamelCase()
        val output = target.layout.buildDirectory.dir("generated/sources/ktorClient/$folderName/")
        ktorClientInput.outputFolder.convention(output)
        ktorClientInput.parcelable.convention(true)
        ktorClientInput.optInKtorBinaryType.convention(false)
        ktorClientInput.transformerSpec.endpointTransformer.convention(DefaultEndpointTransformer::class.java)
        ktorClientInput.transformerSpec.ktorMapper.convention(DefaultKtorControllerMapper::class.java)
        ktorClientInput.transformerSpec.ktorTransformer.convention(DefaultKtorTransformer::class.java)

        // register task(s)
        val taskName = "generate${ktorClientInput.name.toPascalCase()}KtorClient"
        val task = target.tasks.register(taskName, GenerateKtorClientTask::class.java) {
            it.log.set(ktorClient.log)
            it.clientName.set(ktorClientInput.name)
            it.packageName.set(ktorClientInput.packageName)
            it.specFile.set(ktorClientInput.specFile)
            it.schemasFolder.set(ktorClientInput.schemasFolder)
            it.outputFolder.set(ktorClientInput.outputFolder)
            it.parcelable.set(false)
            it.optInKtorBinaryType.set(false)
            it.transformerSpec.endpointTransformer.set(ktorClientInput.transformerSpec.endpointTransformer)
            it.transformerSpec.ktorMapper.set(ktorClientInput.transformerSpec.ktorMapper)
            it.transformerSpec.ktorTransformer.set(ktorClientInput.transformerSpec.ktorTransformer)
        }
        baseTask.configure { it.dependsOn(task) }

        // wire task output to the main source set
        target.registerAndroid(task, ktorClientInput.parcelable, ktorClientInput.optInKtorBinaryType)
        target.registerKmp(task)
        target.registerJvm(task) { optInKtorBinaryType.set(ktorClientInput.optInKtorBinaryType) }
        target.afterEvaluate {
            val supportsOptionalBinaryType =
                !target.pluginManager.hasPlugin("org.jetbrains.kotlin.multiplatform") &&
                    (
                        target.pluginManager.hasPlugin("org.jetbrains.kotlin.jvm") ||
                            target.pluginManager.hasPlugin("com.android.application") ||
                            target.pluginManager.hasPlugin("com.android.library")
                        )

            if (supportsOptionalBinaryType && !ktorClientInput.optInKtorBinaryType.get()) {
                target.logger.warn(
                    "/!\\ Generate Client - " +
                        "Ktor binary support is not enabled for `${ktorClientInput.name.toPascalCase()}`. " +
                        "Generated clients will use the deprecated `InputStream` type, " +
                        "which will be replaced by `ByteReadChannel` in version 3.0. Enable `optInKtorBinaryType` to migrate.",
                )
            }
        }
    }
}
