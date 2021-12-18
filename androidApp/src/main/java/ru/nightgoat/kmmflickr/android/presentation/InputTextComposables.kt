package ru.nightgoat.kmmflickr.android.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import ru.nightgoat.kmmflickr.android.R


@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    textInput: String,
    hint: String,
    onTextInputChange: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        value = textInput,
        onValueChange = {
            onTextInputChange(it)
        },
        placeholder = {
            Text(hint)
        },
        trailingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                contentDescription = "search",
                colorFilter = ColorFilter.tint(Color.Black)
            )
        },
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClick()
            }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        singleLine = true
    )
}