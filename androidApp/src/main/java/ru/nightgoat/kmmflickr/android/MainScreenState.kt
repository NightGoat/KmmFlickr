package ru.nightgoat.kmmflickr.android

import ru.nightgoat.kmmflickr.models.ui.PhotoUi

sealed class MainScreenState {
    object Loading : MainScreenState()
    object Test : MainScreenState()
    data class Error(val errorMessage: String) : MainScreenState()
    data class Images(val list: List<PhotoUi> = emptyList()) : MainScreenState()
}