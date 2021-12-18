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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.nightgoat.kmmflickr.android.presentation.SearchTextField
import ru.nightgoat.kmmflickr.android.presentation.SimpleSpacer
import ru.nightgoat.kmmflickr.android.presentation.VerticalGallery

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.screenState.collectAsState()
            val searchText by viewModel.searchTextInput.collectAsState()
            MainComposable(
                state = state,
                searchTextInput = searchText,
                onSearchTextInputChange = {
                    viewModel.changeSearchTextInput(it)
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
                    .padding(8.dp)
            ) {
                SearchTextField(
                    textInput = searchTextInput,
                    hint = "search image",
                    onTextInputChange = onSearchTextInputChange,
                    onSearchClick = onSearchClick
                )
                SimpleSpacer(size = 8)
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
        is MainScreenState.Test -> VerticalGallery(
            urls = List(5) { "" },
            isTest = true
        )
    }
}


@Preview
@Composable
private fun MainComposablePreview() {
    val state = MainScreenState.Test
    MainComposable(state = state)
}