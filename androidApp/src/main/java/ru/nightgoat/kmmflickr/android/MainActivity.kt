package ru.nightgoat.kmmflickr.android

import android.content.ContentValues
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import io.github.aakira.napier.Napier
import ru.nightgoat.kmmflickr.android.presentation.composables.*
import ru.nightgoat.kmmflickr.android.presentation.theme.FlickrTheme
import ru.nightgoat.kmmflickr.android.presentation.theme.defaultPadding
import ru.nightgoat.kmmflickr.core.constants.MimeTypes
import ru.nightgoat.kmmflickr.models.ui.PhotoUi
import ru.nightgoat.kmmflickr.providers.strings.StringProvider
import ru.nightgoat.kmmflickr.providers.strings.stringDictionary
import java.io.IOException
import java.util.*

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocale()
        setContent {
            MainContent()
        }
    }

    @Composable
    private fun MainContent() {
        val context = LocalContext.current
        val state by viewModel.screenState.collectAsState()
        val searchText by viewModel.searchTextInput.collectAsState()
        val currentFocus = LocalFocusManager.current
        FlickrTheme {
            MainComposable(
                state = state,
                searchTextInput = searchText,
                onSearchTextInputChange = viewModel::changeSearchTextInput,
                onSearchClick = {
                    viewModel.search()
                    currentFocus.clearFocus()
                },
                onCardClick = viewModel::onCardClick,
                onClickErase = viewModel::clearTextField,
            )

            SideEffects(context)
        }
    }

    @Composable
    private fun SideEffects(context: Context) {
        when (val sideEffect = viewModel.sideEffect.collectAsState(MainSideEffect.Empty).value) {
            is MainSideEffect.ShowImageDescription -> {
                val photo = sideEffect.photoUi
                ImageDescription(
                    photoUi = photo,
                    onCancelSave = viewModel::clearSideEffect,
                    onSaveClick = {
                        viewModel.downloadImage(photo)
                    }
                )
            }
            is MainSideEffect.Toast -> Toast(
                sideEffect = sideEffect,
                context = context
            )
            is MainSideEffect.SnackBar -> SnackBarWithActionOnBottom(
                text = sideEffect.message,
                actionText = stringDictionary.retry,
                onActionClick = sideEffect.onAction
            )
            is MainSideEffect.SaveImageToGallery -> {
                LaunchedEffect(sideEffect) {
                    saveImageToGallery(
                        context = context,
                        bitmap = sideEffect.bitmap,
                        displayName = sideEffect.photoUi.id
                    )
                }
            }
            MainSideEffect.Empty -> Unit
        }
    }

    @Composable
    private fun Toast(
        sideEffect: MainSideEffect.Toast,
        context: Context
    ) {
        LaunchedEffect(sideEffect) {
            Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setLocale()
    }

    /**
     * Method checks current locale and provides new string dictionary
     * */
    private fun setLocale() {
        StringProvider.initWithLanguage(Locale.getDefault().language)
    }

    private fun saveImageToGallery(
        context: Context,
        bitmap: Bitmap,
        format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
        mimeType: MimeTypes = MimeTypes.JPEG,
        displayName: String
    ) {
        var operationMessage = stringDictionary.imageSavedSuccessfully
        val values = getContentValues(displayName, mimeType)
        var uri: Uri? = null
        val contentResolver = context.contentResolver
        runCatching {
            with(contentResolver) {
                insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)?.also {
                    uri = it // Keep uri reference so it can be removed on failure

                    openOutputStream(it)?.use { stream ->
                        if (!bitmap.compress(format, 95, stream)) {
                            throw IOException("Failed to save bitmap.")
                        }
                    } ?: throw IOException("Failed to open output stream.")
                } ?: throw IOException("Failed to create new MediaStore record.")
            }
        }.onFailure {
            uri?.let { orphanUri ->
                // Don't leave an orphan entry in the MediaStore
                contentResolver.delete(orphanUri, null, null)
            }
            Napier.e("Save image to gallery exception", it)
            operationMessage = stringDictionary.imageSaveError
        }
        Toast.makeText(context, operationMessage, Toast.LENGTH_SHORT).show()
    }

    private fun getContentValues(
        displayName: String,
        mimeType: MimeTypes
    ): ContentValues {
        return ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$displayName${mimeType.extension}")
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType.value)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
            }
        }
    }
}

/**
 * For easy preview purposes all methods has default realisation
 * */
@Composable
private fun MainComposable(
    state: MainScreenState,
    searchTextInput: String = "",
    onSearchTextInputChange: (String) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onCardClick: (String) -> Unit = {},
    onClickErase: () -> Unit = {},
) {
        Box {
            Column(
                modifier = Modifier
                    .background(color = Color.LightGray)
                    .fillMaxSize()
                    .padding(defaultPadding)
            ) {
                SearchTextField(
                    textInput = searchTextInput,
                    hint = stringDictionary.searchHint,
                    onTextInputChange = onSearchTextInputChange,
                    onSearchClick = onSearchClick,
                    onClickErase = onClickErase
                )
                SimpleSpacer(defaultPadding)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    MainState(state, onCardClick)
                }
            }
        }
}

@Composable
private fun MainState(state: MainScreenState, onCardClick: (String) -> Unit) {
    when (state) {
        is MainScreenState.Loading -> CircularProgressIndicator()
        is MainScreenState.Images -> VerticalGallery(
            urls = state.photos,
            onPhotoClick = onCardClick
        )
        is MainScreenState.NothingFound -> Text(stringDictionary.nothingFound)
    }
}

@Preview
@Composable
private fun MainComposablePreview() {
    val photos = List(5) {
        PhotoUi.fake
    }
    val state = MainScreenState.Images(photos)
    MainComposable(state = state)
}