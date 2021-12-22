package ru.nightgoat.kmmflickr.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soywiz.korim.format.toAndroidBitmap
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.nightgoat.kmmflickr.domain.IDownloadImageUseCase
import ru.nightgoat.kmmflickr.domain.IGetImagesUseCase
import ru.nightgoat.kmmflickr.models.ui.PhotoUi

class MainViewModel : ViewModel(), KoinComponent {

    val screenState = MutableStateFlow<MainScreenState>(MainScreenState.Loading)
    val sideEffect = MutableSharedFlow<MainSideEffect>(replay = 1, extraBufferCapacity = 1)

    private val currentStateValue
        get() = screenState.value

    val getImagesUseCase: IGetImagesUseCase by inject()
    val downloadImageUseCase: IDownloadImageUseCase by inject()

    val searchTextInput = MutableStateFlow(DEFAULT_SEARCH_TEXT)

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
            getImagesUseCase(searchText).onSuccess { images ->
                handleGetImagesSuccess(images)
            }.onFailure { throwable ->
                handleGetImagesFailure(throwable)
            }
        }
    }

    private fun handleGetImagesFailure(it: Throwable) {
        MainScreenState.NothingFound.setToScreen()
        MainSideEffect.SnackBar(it.localizedMessage.orEmpty()) {
            search()
            clearSideEffect()
        }.reduce()
    }

    private fun handleGetImagesSuccess(images: List<PhotoUi>) {
        if (images.isEmpty()) {
            MainScreenState.NothingFound.setToScreen()
        } else {
            MainScreenState.Images(images).setToScreen()
        }
    }

    fun clearTextField() {
        changeSearchTextInput()
    }

    fun changeSearchTextInput(newText: String = "") {
        searchTextInput.value = newText
    }

    fun onCardClick(cardId: String) {
        val state = currentStateValue
        if (state is MainScreenState.Images) {
            val photos = state.photos
            photos.find { photo ->
                photo.id == cardId
            }?.let { photo ->
                MainSideEffect.ShowImageDescription(photo).reduce()
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

    fun downloadImage(photoUi: PhotoUi) {
        viewModelScope.launch {
            clearSideEffect()
            downloadImageUseCase(photoUi).onSuccess { korimBitmap ->
                val bitmap = korimBitmap.toAndroidBitmap()
                MainSideEffect.SaveImageToGallery(
                    bitmap = bitmap,
                    photoUi = photoUi
                ).reduce()
            }
        }
    }


    companion object {
        const val DEFAULT_SEARCH_TEXT = "Electrolux"
    }
}