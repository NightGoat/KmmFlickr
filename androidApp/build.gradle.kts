plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "ru.nightgoat.kmmflickr.android"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = config.Versions.compose
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(kotlin("stdlib-jdk8", config.Versions.kotlin))
    api(config.Libs.Compose.ui)
    api(config.Libs.Compose.material)
    api(config.Libs.Compose.tooling)
    api(config.Libs.Compose.activity)
    api(config.Libs.Compose.navigation)
    api(config.Libs.Compose.viewModel)
    api(config.Libs.Compose.runtime)
    api(config.Libs.Compose.coil)

    api(config.Libs.Core.appCompat)
    api(config.Libs.Core.material)
    api(config.Libs.Common.coroutines)
}