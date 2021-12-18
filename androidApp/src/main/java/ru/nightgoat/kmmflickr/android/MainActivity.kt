package ru.nightgoat.kmmflickr.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import ru.nightgoat.kmmflickr.android.presentation.SearchTextField
import ru.nightgoat.kmmflickr.android.presentation.SimpleSpacer
import ru.nightgoat.kmmflickr.android.presentation.VerticalGallery
import ru.nightgoat.kmmflickr.android.presentation.defaultPadding
import ru.nightgoat.kmmflickr.models.ui.PhotoUi
import ru.nightgoat.kmmflickr.models.util.Url

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.screenState.collectAsState()
            val searchText by viewModel.searchTextInput.collectAsState()
            val currentFocus = LocalFocusManager.current
            MainComposable(
                state = state,
                searchTextInput = searchText,
                onSearchTextInputChange = {
                    viewModel.changeSearchTextInput(it)
                },
                onSearchClick = {
                    viewModel.search()
                    currentFocus.clearFocus()
                }
            )
        }
    }
}

@Composable
fun MainComposable(
    state: MainScreenState,
    searchTextInput: String = "",
    onSearchTextInputChange: (String) -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    Surface {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Flickr") },
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(defaultPadding)
            ) {
                SearchTextField(
                    textInput = searchTextInput,
                    hint = "search image",
                    onTextInputChange = onSearchTextInputChange,
                    onSearchClick = onSearchClick
                )
                SimpleSpacer(defaultPadding)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    ReduceState(state)
                }
            }

        }
    }
}

@Composable
private fun ReduceState(state: MainScreenState) {
    when (state) {
        is MainScreenState.Loading -> CircularProgressIndicator()
        is MainScreenState.Error -> Text(state.errorMessage)
        is MainScreenState.Images -> VerticalGallery(urls = state.list)
    }
}


@Preview
@Composable
private fun MainComposablePreview() {
    val photos = List(5) {
        PhotoUi(
            url = Url(link = "https://upload.wikimedia.org/wikipedia/commons/c/c4/Surrogate%27s_Court_Splendor.jpg"),
            description = "",
            width = 300,
            height = 400
        )
    }
    val state = MainScreenState.Images(photos)
    MainComposable(state = state)
}