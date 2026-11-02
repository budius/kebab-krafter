import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	alias(libs.plugins.androidApplication)
	alias(libs.plugins.composeCompiler)
	alias(libs.plugins.kotlin.serialization)
	alias(libs.plugins.kotlin.parcelize)
	alias(libs.plugins.ktlint)

	id("io.github.budius.kebab-krafter") version "1.0-SNAPSHOT"
}

kotlin {
	compilerOptions {
		jvmTarget = JvmTarget.JVM_11
	}
}
dependencies {
	implementation(project(":shared"))

	implementation(libs.androidx.activity.compose)

	implementation(libs.kotlinx.serialization)
	implementation(libs.ktor.client.core)
	implementation(libs.ktor.client.okhttp)
	implementation(libs.ktor.client.encoding)
	implementation(libs.ktor.client.logging)
	implementation(libs.ktor.client.content.negotiation)
	implementation(libs.ktor.client.serialization.kotlinx.json)

	implementation(libs.compose.uiToolingPreview)
	debugImplementation(libs.compose.uiTooling)

	testImplementation(libs.junit)
	testImplementation(libs.kotlin.reflect)
}

android {
	namespace = "io.github.budius.kebabkrafterkmp"
	compileSdk = libs.versions.android.compileSdk.get().toInt()

	defaultConfig {
		applicationId = "io.github.budius.kebabkrafterkmp"
		minSdk = libs.versions.android.minSdk.get().toInt()
		targetSdk = libs.versions.android.targetSdk.get().toInt()
		versionCode = 1
		versionName = "1.0"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro",
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	buildFeatures {
		compose = true
	}
}

ktorClient {
	log = true

	create("petStore") {
		packageName = "io.github.budius.kebabkrafterkmp.gen.android.petstore"
		specFile = File(rootDir, "../sample-resources/petstore/swagger.yml")
		schemasFolder = File(rootDir, "../sample-resources/petstore/models")
		parcelable = true
	}
}
