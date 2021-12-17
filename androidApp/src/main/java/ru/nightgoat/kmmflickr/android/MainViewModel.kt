package ru.nightgoat.kmmflickr.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.nightgoat.kmmflickr.Greeting

class MainViewModel : ViewModel() {

    val screenState = MutableStateFlow<MainScreenState>(MainScreenState.Loading)

    init {
        startLoading()
    }

    private fun startLoading() {
        viewModelScope.launch {
            MainScreenState.Loading.setToScreen()
            kotlin.runCatching {
                Greeting().greeting()
            }.onSuccess {
                MainScreenState.Images(listOf(it)).setToScreen()
            }.onFailure {
                MainScreenState.Error(it).setToScreen()
            }
        }
    }

    private fun MainScreenState.setToScreen() {
        screenState.value = this
    }
}