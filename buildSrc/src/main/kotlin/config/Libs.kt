package config

object Libs {
    object Core {
        const val appCompat = "androidx.appcompat:appcompat:1.3.1"
        const val material = "com.google.android.material:material:1.4.0"
    }

    object Common {
        const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
        const val napier = "io.github.aakira:napier:${Versions.napier}"
    }

    object Ktor {
        const val core = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val serialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
        const val logging = "io.ktor:ktor-client-logging:${Versions.ktor}"
        const val android = "io.ktor:ktor-client-android:${Versions.ktor}"
        const val iOs = "io.ktor:ktor-client-ios:${Versions.ktor}"
    }

    object Tests {
        const val junit = "junit:junit:${Versions.junit}"
        const val compose = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    }

    object Compose {
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val activity = "androidx.activity:activity-compose:${Versions.composeActivity}"
        const val navigation =
            "androidx.navigation:navigation-compose:${Versions.composeNavigation}"
        const val viewModel =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModel}"
        const val runtime = "androidx.compose.runtime:runtime:${Versions.compose_runtime}"
        const val coil = "io.coil-kt:coil-compose:${Versions.coil}"
    }

    object DI {
        const val koinCore = "io.insert-koin:koin-core:${Versions.koin}"
        const val koinAndroid = "io.insert-koin:koin-android:${Versions.koin}"
    }

    object Accompanist {
        const val swiperefresh =
            "com.google.accompanist:accompanist-swiperefresh:${Versions.accompanist}"
    }
}
