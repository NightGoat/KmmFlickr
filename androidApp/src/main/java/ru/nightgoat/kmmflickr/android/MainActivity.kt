package ru.nightgoat.kmmflickr.android

import android.content.res.Configuration
import android.os.Bundle
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
import ru.nightgoat.kmmflickr.android.presentation.*
import ru.nightgoat.kmmflickr.android.presentation.theme.FlickrTheme
import ru.nightgoat.kmmflickr.models.ui.PhotoUi
import ru.nightgoat.kmmflickr.providers.strings.StringProvider
import ru.nightgoat.kmmflickr.providers.strings.stringDictionary
import java.util.*

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocale()
        setContent {
            val context = LocalContext.current
            val state by viewModel.screenState.collectAsState()
            val sideEffect by viewModel.sideEffect.collectAsState(MainSideEffect.Empty)
            val searchText by viewModel.searchTextInput.collectAsState()
            val currentFocus = LocalFocusManager.current
            MainComposable(
                state = state,
                sideEffect = sideEffect,
                searchTextInput = searchText,
                onSearchTextInputChange = viewModel::changeSearchTextInput,
                onSearchClick = {
                    viewModel.search()
                    currentFocus.clearFocus()
                },
                onCardClick = viewModel::onCardClick,
                onClickErase = viewModel::clearTextField,
                onCancelSave = viewModel::clearSideEffect,
                onSaveClick = { photo ->
                    viewModel.savePhoto(context, photo)
                }
            )
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
}

/**
 * For easy preview purposes all methods has default realisation
 * */
@Composable
private fun MainComposable(
    state: MainScreenState,
    sideEffect: MainSideEffect,
    searchTextInput: String = "",
    onSearchTextInputChange: (String) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onCardClick: (String) -> Unit = {},
    onClickErase: () -> Unit = {},
    onCancelSave: () -> Unit = {},
    onSaveClick: (PhotoUi) -> Unit = {},
) {
    val context = LocalContext.current
    FlickrTheme {
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

            when (sideEffect) {
                is MainSideEffect.ShowImageDescription -> {
                    val photo = sideEffect.photoUi
                    ImageDescription(photo, onCancelSave) {
                        onSaveClick(photo)
                    }
                }
                is MainSideEffect.Toast -> LaunchedEffect(sideEffect) {
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                }
                is MainSideEffect.SnackBar -> SnackBarWithActionOnBottom(
                    text = sideEffect.message,
                    actionText = stringDictionary.retry,
                    onActionClick = sideEffect.onAction
                )
                MainSideEffect.Empty -> Unit
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
    val sideEffect = MainSideEffect.Empty
    MainComposable(state = state, sideEffect = sideEffect)
}