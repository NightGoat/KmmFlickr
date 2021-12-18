package ru.nightgoat.kmmflickr.android

sealed class MainScreenState {
    object Loading : MainScreenState()
    object Test : MainScreenState()
    data class Error(val errorMessage: String) : MainScreenState()
    data class Images(val list: List<String> = emptyList()) : MainScreenState()
}