package com.diconium.mobile.tools.kebabkrafter.plugin

import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.SourceDirectories
import com.diconium.mobile.tools.kebabkrafter.EndpointTransformer
import com.diconium.mobile.tools.kebabkrafter.KtorController
import com.diconium.mobile.tools.kebabkrafter.KtorTransformer
import com.diconium.mobile.tools.kebabkrafter.models.Endpoint
import com.diconium.mobile.tools.kebabkrafter.plugin.client.GenerateKtorClientTask
import org.gradle.api.Project
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.provider.Property
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskProvider
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetContainer

//region default transformers
internal class DefaultEndpointTransformer : EndpointTransformer {
    override fun transform(endpoint: Endpoint) = endpoint
}

internal class DefaultKtorTransformer : KtorTransformer {
    override fun transform(endpoint: Endpoint, controller: KtorController) = controller
}
//endregion

//region those are copied from those auto-generated accessors files,
// just to make the usage above a bit cleaner.
private val SourceSet.kotlin: SourceDirectorySet
    get() = (this as ExtensionAware).extensions.getByName("kotlin")
        as SourceDirectorySet

//endregion
internal fun Project.registerServer(task: Any) {
    pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
        extensions.configure<SourceSetContainer>("sourceSets") { container ->
            container.named("main").configure { sourceSet ->
                sourceSet.java.srcDirs(task)
                sourceSet.kotlin.srcDirs(task)
            }
        }
    }
}

internal fun Project.registerClientJvm(
    task: TaskProvider<GenerateKtorClientTask>,
    optInKtorBinaryType: Property<Boolean>,
) {
    pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
        extensions.configure<SourceSetContainer>("sourceSets") { container ->
            container.named("main").configure { sourceSet ->
                sourceSet.java.srcDirs(task)
                sourceSet.kotlin.srcDirs(task)
            }
        }
        // KMP `desktopApp` and `server` can use whatever type
        task.configure { it.optInKtorBinaryType.set(optInKtorBinaryType) }
    }
}

internal fun Project.registerKmp(task: TaskProvider<GenerateKtorClientTask>) {
    pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
        extensions.configure<KotlinSourceSetContainer>("kotlin") { kotlin ->
            kotlin.sourceSets.named("commonMain").configure { it.kotlin.srcDir(task) }
        }
        // Ktor Binary type is not optional for KMP `webApp`, `iosApp` amd `shared` modules
        task.configure { it.optInKtorBinaryType.set(true) }
    }
}

internal fun Project.registerAndroid(
    task: TaskProvider<GenerateKtorClientTask>,
    parcelable: Property<Boolean>,
    optInKtorBinaryType: Property<Boolean>,
) {
    fun SourceDirectories.Flat.registerVariant() {
        this.addGeneratedSourceDirectory(task, GenerateKtorClientTask::outputFolder)
    }

    fun register() {
        task.configure { it.parcelable.set(parcelable) }
        task.configure { it.optInKtorBinaryType.set(optInKtorBinaryType) }
        extensions.getByType(AndroidComponentsExtension::class.java).onVariants { variant ->
            variant.sources.kotlin?.apply { registerVariant() }
                ?: run { variant.sources.java?.registerVariant() }
        }
    }

    pluginManager.withPlugin("com.android.application") { register() }
    pluginManager.withPlugin("com.android.library") { register() }
}
