package ru.nightgoat.kmmflickr.android.presentation.util

import androidx.compose.ui.graphics.Color

data class ImageData(
    val drawableResId: Int,
    val description: String = "",
    val color: Color = Color.Black
)
