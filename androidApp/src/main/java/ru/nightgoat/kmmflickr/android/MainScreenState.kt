package ru.nightgoat.kmmflickr.android

import ru.nightgoat.kmmflickr.core.base.ScreenState
import ru.nightgoat.kmmflickr.models.ui.PhotoUi

/**
 * Main screen state object. If you want to show loading and data, then you can make Loading
 * data class and put in it photos from Images, or just make it SideEffect.
 * */
sealed class MainScreenState : ScreenState {
    object Loading : MainScreenState()
    object NothingFound : MainScreenState()
    data class Images(val photos: List<PhotoUi> = emptyList()) : MainScreenState()
}