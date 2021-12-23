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
import ru.nightgoat.kmmflickr.providers.strings.stringDictionary
import kotlin.coroutines.CoroutineContext

class BaseViewModel : IBaseViewModel {

    private val superVisorJob = SupervisorJob()
    private var snackBarJob: Job? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + superVisorJob

    override val container: Container<MainScreenState, MainSideEffect> =
        container(MainScreenState.Loading) {
            launch {
                searchImagesForDefaultTag()
            }
        }

    val getImagesUseCase: IGetImagesUseCase by inject()
    override val downloadImageUseCase: IDownloadImageUseCase by inject()

    private val currentStateValue
        get() = container.stateFlow.value

    private val _searchTextInput = MutableStateFlow(DEFAULT_SEARCH_TAG)
    override val searchTextInput = _searchTextInput.asStateFlow()

    private val previousSearchedText = MutableStateFlow("")

    private suspend fun searchImagesForDefaultTag() {
        searchTag()
    }

    override suspend fun searchTag(tagToSearch: String?) {
        val searchText = tagToSearch ?: searchTextInput.value
        val previousSearch = previousSearchedText.value
        when {
            searchText.isEmpty() -> MainSideEffect.Toast(stringDictionary.pleaseEnterSomething)
                .reduce()
            searchText != previousSearch -> startLoadingImages(searchText)
        }
    }

    private suspend fun startLoadingImages(searchText: String) {
        MainScreenState.Loading.setToScreen()
        getImagesUseCase(searchText).onSuccess { images ->
            handleGetImagesSuccess(images)
        }.onFailure { throwable ->
            handleGetImagesFailure(throwable)
        }
    }

    private fun handleGetImagesFailure(it: Throwable) {
        MainScreenState.NothingFound.setToScreen()
        MainSideEffect.SnackBar(it.message.orEmpty()) {
            snackBarJob?.cancel()
            snackBarJob = launch {
                searchTag()
                clearSideEffect()
            }
        }.reduce()
    }

    private fun handleGetImagesSuccess(images: List<PhotoUi>) {
        saveSearchRequest()
        if (images.isEmpty()) {
            MainScreenState.NothingFound.setToScreen()
        } else {
            MainScreenState.Images(images).setToScreen()
        }
    }

    /** Saves text input to previous searched text field */
    private fun saveSearchRequest() {
        val oldValue = _searchTextInput.value
        previousSearchedText.value = oldValue
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
            searchTag()
        }
    }

    override fun clearJobsAndSubscriptions() {
        superVisorJob.cancel()
        snackBarJob?.cancel()
        snackBarJob == null
    }

    companion object {
        const val DEFAULT_SEARCH_TAG = "Electrolux"
    }
}

interface IBaseViewModel : ContainerHost<MainScreenState, MainSideEffect>, KoinComponent,
    CoroutineScope {
    override val container: Container<MainScreenState, MainSideEffect>
    val downloadImageUseCase: IDownloadImageUseCase
    val searchTextInput: StateFlow<String>
    fun onSearchClick()
    fun clearSideEffect()
    fun onCardClick(cardId: String)
    fun changeSearchTextInput(newText: String = "")
    fun clearTextField()
    suspend fun searchTag(tagToSearch: String? = null)
    fun MainSideEffect.reduce()
    fun MainScreenState.setToScreen()

    /** There you should cancel all your flow subscriptions and etc when you don't need them */
    fun clearJobsAndSubscriptions()
}