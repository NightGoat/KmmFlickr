package ru.nightgoat.kmmflickr.android

sealed class MainScreenState {
    object Loading : MainScreenState()
    data class Error(val throwable: Throwable) : MainScreenState()
    data class Images(val list: List<String> = emptyList()) : MainScreenState()
}