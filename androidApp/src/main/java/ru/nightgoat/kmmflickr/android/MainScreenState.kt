package ru.nightgoat.kmmflickr.android

import ru.nightgoat.kmmflickr.core.base.ScreenState
import ru.nightgoat.kmmflickr.models.ui.PhotoUi

sealed class MainScreenState : ScreenState {
    object Loading : MainScreenState()
    data class Images(val photos: List<PhotoUi> = emptyList()) : MainScreenState()
}