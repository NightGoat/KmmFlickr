package ru.nightgoat.kmmflickr.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.nightgoat.kmmflickr.domain.IGetImagesUseCase

class MainViewModel : ViewModel(), KoinComponent {

    val screenState = MutableStateFlow<MainScreenState>(MainScreenState.Loading)
    val sideEffect = MutableSharedFlow<MainSideEffect>(replay = 1, extraBufferCapacity = 1)

    private val currentStateValue
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
            val searchText = searchTextInput.value
            getImagesUseCase(searchText).onSuccess {
                MainScreenState.Images(it).setToScreen()
            }.onFailure {
                MainSideEffect.SnackBar(it.localizedMessage.orEmpty()) {
                    search()
                }.reduce()
            }
        }
    }

    fun clearTextField() {
        changeSearchTextInput("")
    }

    fun changeSearchTextInput(newText: String) {
        searchTextInput.value = newText
    }

    fun onCardClick(cardId: String) {
        (currentStateValue as? MainScreenState.Images)?.let { it ->
            val photos = it.photos
            photos.find { photo ->
                photo.id == cardId
            }?.let { photo ->
                MainSideEffect.SaveImage(photo).reduce()
            }
        }
    }

    private fun MainSideEffect.reduce() {
        viewModelScope.launch {
            sideEffect.emit(this@reduce)
        }
    }

    fun clearSideEffect() {
        MainSideEffect.Empty.reduce()
    }

    private fun MainScreenState.setToScreen() {
        screenState.value = this
    }

}