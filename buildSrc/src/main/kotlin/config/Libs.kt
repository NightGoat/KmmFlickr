package config

object Libs {
    object Core {
        const val appCompat = "androidx.appcompat:appcompat:1.3.1"
        const val material = "com.google.android.material:material:1.4.0"
    }

    object Common {
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
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

    object Accompanist {
        const val swiperefresh =
            "com.google.accompanist:accompanist-swiperefresh:${Versions.accompanist}"
    }
}
