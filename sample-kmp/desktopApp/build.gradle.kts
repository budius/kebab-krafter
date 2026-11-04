import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
	alias(libs.plugins.kotlinJvm)
	alias(libs.plugins.composeMultiplatform)
	alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktlint)

    id("io.github.budius.kebab-krafter") version "1.0-SNAPSHOT"
}

dependencies {
	implementation(project(":shared"))

	implementation(compose.desktop.currentOs)
	implementation(libs.kotlinx.coroutines.swing)

    implementation(libs.bundles.ktor.client)
    implementation(libs.kotlinx.serialization)
	implementation(libs.compose.uiToolingPreview)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
}

compose.desktop {
	application {
		mainClass = "io.github.budius.kebabkrafterkmp.MainKt"

		nativeDistributions {
			targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
			packageName = "io.github.budius.kebabkrafterkmp"
			packageVersion = "1.0.0"
		}
	}
}

ktorClient {
    log = true

    create("petStore") {
        packageName = "io.github.budius.kebabkrafterkmp.gen.desktop.petstore"
        specFile = File(rootDir, "../sample-resources/petstore/swagger.yml")
        schemasFolder = File(rootDir, "../sample-resources/petstore/models")
        optInKtorBinaryType = true
    }
}
