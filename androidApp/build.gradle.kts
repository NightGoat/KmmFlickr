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
    implementation(config.Libs.Compose.ui)
    implementation(config.Libs.Compose.material)
    implementation(config.Libs.Compose.tooling)
    implementation(config.Libs.Compose.activity)
    implementation(config.Libs.Compose.navigation)
    implementation(config.Libs.Compose.viewModel)
    implementation(config.Libs.Compose.runtime)
    implementation(config.Libs.Compose.coil)
    implementation(config.Libs.Core.appCompat)
    implementation(config.Libs.Core.material)
    implementation(config.Libs.DI.koinAndroid)
    implementation(config.Libs.Common.coroutinesAndroid)
    implementation(config.Libs.Common.korimAndroid)
}