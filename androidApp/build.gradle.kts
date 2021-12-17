plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "ru.nightgoat.kmmflickr.android"
        minSdk = 21
        targetSdk = 32
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
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")

    val kotlinCoroutines = "1.5.0"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutines")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlinCoroutines")
}