package ru.nightgoat.kmmflickr.presentation

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import ru.nightgoat.kmmflickr.domain.IDownloadImageUseCase
import ru.nightgoat.kmmflickr.domain.IGetImagesUseCase
import ru.nightgoat.kmmflickr.models.ui.PhotoUi
import kotlin.coroutines.CoroutineContext

class BaseViewModel : IBaseViewModel {

    override val superVisorJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + superVisorJob

    override val container: Container<MainScreenState, MainSideEffect> =
        container(MainScreenState.Loading) {
            launch {
                startLoading()
            }
        }

    val getImagesUseCase: IGetImagesUseCase by inject()
    override val downloadImageUseCase: IDownloadImageUseCase by inject()

    private val currentStateValue
        get() = container.stateFlow.value

    private val _searchTextInput = MutableStateFlow(DEFAULT_SEARCH_TEXT)
    override val searchTextInput = _searchTextInput.asStateFlow()

    private val previousSearchedText = MutableStateFlow("")

    private suspend fun startLoading() {
        search()
    }

    override suspend fun search() {
        val searchText = searchTextInput.value
        val previousSearch = previousSearchedText.value
        if (searchText != previousSearch) {
            MainScreenState.Loading.setToScreen()
            getImagesUseCase(searchText).onSuccess { images ->
                handleGetImagesSuccess(images)
            }.onFailure { throwable ->
                handleGetImagesFailure(throwable)
            }
        }
    }

    private suspend fun handleGetImagesFailure(it: Throwable) {
        MainScreenState.NothingFound.setToScreen()
        MainSideEffect.SnackBar(it.message.orEmpty()) {
            search()
            clearSideEffect()
        }.reduce()
    }

    private fun handleGetImagesSuccess(images: List<PhotoUi>) {
        val oldValue = _searchTextInput.value
        previousSearchedText.value = oldValue //TODO move to another method
        if (images.isEmpty()) {
            MainScreenState.NothingFound.setToScreen()
        } else {
            MainScreenState.Images(images).setToScreen()
        }
    }

    override fun clearTextField() {
        changeSearchTextInput()
    }

    override fun changeSearchTextInput(newText: String) {
        _searchTextInput.value = newText
    }

    override fun onCardClick(cardId: String) {
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

    override fun MainSideEffect.reduce() {
        intent {
            postSideEffect(this@reduce)
        }
    }

    override fun clearSideEffect() {
        MainSideEffect.Empty.reduce()
    }

    override fun MainScreenState.setToScreen() {
        intent {
            reduce {
                this@setToScreen
            }
        }
    }

    override fun onSearchClick() {
        launch {
            search()
        }
    }

    override fun startSnackBarAction(action: suspend () -> Unit) {
        launch {
            action()
        }
    }

    companion object {
        const val DEFAULT_SEARCH_TEXT = "Electrolux"
    }
}

interface IBaseViewModel : ContainerHost<MainScreenState, MainSideEffect>, KoinComponent,
    CoroutineScope {
    val superVisorJob: Job
    override val container: Container<MainScreenState, MainSideEffect>
    val downloadImageUseCase: IDownloadImageUseCase
    val searchTextInput: StateFlow<String>
    fun startSnackBarAction(action: suspend () -> Unit)
    fun onSearchClick()
    fun clearSideEffect()
    fun onCardClick(cardId: String)
    fun changeSearchTextInput(newText: String = "")
    fun clearTextField()
    suspend fun search()
    fun MainSideEffect.reduce()
    fun MainScreenState.setToScreen()
}