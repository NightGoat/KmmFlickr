package ru.nightgoat.kmmflickr.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.nightgoat.kmmflickr.domain.IGetImagesUseCase

class MainViewModel : ViewModel(), KoinComponent {

    val screenState = MutableStateFlow<MainScreenState>(MainScreenState.Loading)

    val currentStateValue
        get() = screenState.value

    val getImagesUseCase: IGetImagesUseCase by inject()

    val searchTextInput = MutableStateFlow("Electrolux")

    init {
        startLoading()
    }

    private fun startLoading() {
        search()
    }

    fun search() {
        viewModelScope.launch {
            MainScreenState.Loading.setToScreen()
            getImagesUseCase(searchTextInput.value).onSuccess {
                MainScreenState.Images(it).setToScreen()
            }.onFailure {
                MainScreenState.Error(it.localizedMessage.orEmpty()).setToScreen()
            }
        }
    }

    fun changeSearchTextInput(newText: String) {
        searchTextInput.value = newText
    }

    private fun MainScreenState.setToScreen() {
        screenState.value = this
    }
}