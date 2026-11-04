import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
	alias(libs.plugins.kotlinMultiplatform)
	alias(libs.plugins.composeMultiplatform)
	alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktlint)

    id("io.github.budius.kebab-krafter") version "1.0-SNAPSHOT"
}

kotlin {
	js {
		browser()
		binaries.executable()
	}

	@OptIn(ExperimentalWasmDsl::class)
	wasmJs {
		browser()
		binaries.executable()
	}

	sourceSets {
		commonMain.dependencies {
			implementation(project(":shared"))

			implementation(libs.compose.ui)
            implementation(libs.bundles.ktor.client)
            implementation(libs.kotlinx.serialization)
		}
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
	}
}

ktorClient {
    log = true

    create("petStore") {
        packageName = "io.github.budius.kebabkrafterkmp.gen.web.petstore"
        specFile = File(rootDir, "../sample-resources/petstore/swagger.yml")
        schemasFolder = File(rootDir, "../sample-resources/petstore/models")
    }
}
