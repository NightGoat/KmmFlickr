plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.5.0"
    id("com.android.library")
    id("io.kotest.multiplatform") version "5.0.3"
}

kotlin {
    android()

    listOf(
        iosX64(),
        iosArm64(),
        //iosSimulatorArm64() sure all ios dependencies support this target
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(config.Libs.Ktor.core)
                implementation(config.Libs.Ktor.serialization)
                implementation(config.Libs.Ktor.logging)
                implementation(config.Libs.Common.kotlinReflect)
                api(config.Libs.Common.napier)
                implementation(config.Libs.DI.koinCore)
                implementation(config.Libs.Common.korimShared)
                api(config.Libs.Common.coroutines)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(config.Libs.Tests.ktor)
                implementation(config.Libs.Tests.kotest)
                implementation(config.Libs.Tests.coroutines)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(config.Libs.Ktor.android)
                implementation(config.Libs.Common.korimAndroid)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(config.Libs.Tests.junit)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        //val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            //iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        //val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            //iosSimulatorArm64Test.dependsOn(this)
            dependencies {
                implementation(config.Libs.Ktor.iOs)
            }
        }
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
}