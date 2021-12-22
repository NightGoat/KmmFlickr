package ru.nightgoat.kmmflickr.android

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soywiz.korim.format.toAndroidBitmap
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.nightgoat.kmmflickr.core.constants.MimeTypes
import ru.nightgoat.kmmflickr.domain.IDownloadImageUseCase
import ru.nightgoat.kmmflickr.domain.IGetImagesUseCase
import ru.nightgoat.kmmflickr.models.ui.PhotoUi
import ru.nightgoat.kmmflickr.providers.strings.stringDictionary
import java.io.IOException

class MainViewModel : ViewModel(), KoinComponent {

    val screenState = MutableStateFlow<MainScreenState>(MainScreenState.Loading)
    val sideEffect = MutableSharedFlow<MainSideEffect>(replay = 1, extraBufferCapacity = 1)

    private val currentStateValue
        get() = screenState.value

    val getImagesUseCase: IGetImagesUseCase by inject()
    val downloadImageUseCase: IDownloadImageUseCase by inject()

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
        (currentStateValue as? MainScreenState.Images)?.let { state ->
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

    fun savePhoto(
        context: Context,
        photoUi: PhotoUi
    ) {
        viewModelScope.launch {
            clearSideEffect()
            downloadImageUseCase(photoUi).onSuccess {
                val bitmap = it.toAndroidBitmap()
                var operationMessage = stringDictionary.imageSaveError
                kotlin.runCatching {
                    saveBitmap(
                        context = context,
                        bitmap = bitmap,
                        displayName = photoUi.id
                    )
                }.onSuccess {
                    operationMessage = stringDictionary.imageSavedSuccessfully
                }
                MainSideEffect.Toast(operationMessage).reduce()
            }
        }
    }

    @Throws(IOException::class)
    fun saveBitmap(
        context: Context,
        bitmap: Bitmap,
        format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
        mimeType: MimeTypes = MimeTypes.JPEG,
        displayName: String
    ): Uri {

        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$displayName${mimeType.extension}")
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType.value)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
            }
        }

        var uri: Uri? = null

        return runCatching {
            with(context.contentResolver) {
                insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)?.also {
                    uri = it // Keep uri reference so it can be removed on failure

                    openOutputStream(it)?.use { stream ->
                        if (!bitmap.compress(format, 95, stream))
                            throw IOException("Failed to save bitmap.")
                    } ?: throw IOException("Failed to open output stream.")

                } ?: throw IOException("Failed to create new MediaStore record.")
            }
        }.getOrElse {
            uri?.let { orphanUri ->
                // Don't leave an orphan entry in the MediaStore
                context.contentResolver.delete(orphanUri, null, null)
            }

            throw it
        }
    }

}