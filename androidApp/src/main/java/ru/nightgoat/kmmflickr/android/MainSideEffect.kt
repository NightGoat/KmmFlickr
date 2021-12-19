package ru.nightgoat.kmmflickr.android

import ru.nightgoat.kmmflickr.core.base.SideEffect
import ru.nightgoat.kmmflickr.models.ui.PhotoUi

/**
 * Main purpose of side effect is being called once (mostly with LaunchedEffect)
 * */
sealed class MainSideEffect : SideEffect {
    object Empty : MainSideEffect()
    data class Toast(val message: String) : MainSideEffect()
    data class SnackBar(val message: String, val onAction: () -> Unit) : MainSideEffect()
    data class ShowImageDescription(val photoUi: PhotoUi) : MainSideEffect()
}