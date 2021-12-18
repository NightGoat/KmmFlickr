package ru.nightgoat.kmmflickr.android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import ru.nightgoat.kmmflickr.models.remote.PhotoModel
import ru.nightgoat.kmmflickr.models.ui.PhotoUi
import ru.nightgoat.kmmflickr.models.util.Url

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
                onSaveClick = viewModel::clearSideEffect
            )
        }
    }
}

@Composable
fun MainComposable(
    state: MainScreenState,
    sideEffect: MainSideEffect,
    searchTextInput: String = "",
    onSearchTextInputChange: (String) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onCardClick: (String) -> Unit = {},
    onClickErase: () -> Unit = {},
    onCancelSave: () -> Unit = {},
    onSaveClick: () -> Unit = {},
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Flickr") })
        }
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
                    hint = "search image",
                    onTextInputChange = onSearchTextInputChange,
                    onSearchClick = onSearchClick,
                    onClickErase = onClickErase
                )
                SimpleSpacer(defaultPadding)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    ReduceState(state, onCardClick)
                }
            }

            when (sideEffect) {
                is MainSideEffect.SaveImage -> SaveImageDialog(onCancelSave, onSaveClick)
                is MainSideEffect.Toast -> LaunchedEffect(sideEffect) {
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                }
                is MainSideEffect.SnackBar -> SnackBarWithActionOnBottom(
                    text = sideEffect.message,
                    actionText = "RETRY",
                    onActionClick = sideEffect.onAction
                )
                MainSideEffect.Empty -> Unit
            }
        }
    }

}

@Composable
private fun SaveImageDialog(onCancelSave: () -> Unit, onSaveClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = onCancelSave,
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(defaultPadding)
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = defaultPadding),
                    onClick = onCancelSave
                ) {
                    Text("Нет")
                }
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = onSaveClick
                ) {
                    Text("Да")
                }
            }
        },
        text = {
            Text("Сохранить изображение?")
        },
    )
}

@Composable
private fun ReduceState(state: MainScreenState, onCardClick: (String) -> Unit) {
    when (state) {
        is MainScreenState.Loading -> CircularProgressIndicator()
        is MainScreenState.Images -> VerticalGallery(
            urls = state.photos,
            onPhotoClick = onCardClick
        )
    }
}


@Preview
@Composable
private fun MainComposablePreview() {
    val photos = List(5) {
        PhotoUi(
            model = PhotoModel(),
            url = Url(link = "https://upload.wikimedia.org/wikipedia/commons/c/c4/Surrogate%27s_Court_Splendor.jpg"),
            description = "",
            width = 300,
            height = 400
        )
    }
    val state = MainScreenState.Images(photos)
    val sideEffect = MainSideEffect.Empty
    MainComposable(state = state, sideEffect)
}