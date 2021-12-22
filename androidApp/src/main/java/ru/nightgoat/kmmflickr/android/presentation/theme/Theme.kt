package ru.nightgoat.kmmflickr.android.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = BLUE,
    primaryVariant = RED,
    secondary = GRAY,
    surface = Color.White,
    onSecondary = BLUE,
)

@Composable
fun FlickrTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}