package ru.nightgoat.kmmflickr.android

import ru.nightgoat.kmmflickr.core.base.SideEffect
import ru.nightgoat.kmmflickr.models.ui.PhotoUi

sealed class MainSideEffect : SideEffect {
    object Empty : MainSideEffect()
    data class Toast(val message: String) : MainSideEffect()
    data class SnackBar(val message: String, val onAction: () -> Unit) : MainSideEffect()
    data class SaveImage(val photoUi: PhotoUi) : MainSideEffect()
}