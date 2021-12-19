package ru.nightgoat.kmmflickr.android.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import ru.nightgoat.kmmflickr.android.R
import ru.nightgoat.kmmflickr.providers.strings.stringDictionary

/** Simple searchbar */
@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    textInput: String,
    hint: String = "",
    onTextInputChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onClickErase: () -> Unit
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
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                contentDescription = stringDictionary.searchIcon,
                colorFilter = ColorFilter.tint(Color.Black)
            )
        },
        trailingIcon = {
            if (textInput.isNotEmpty()) {
                Image(
                    modifier = Modifier
                        .clickable {
                            onClickErase()
                        }
                        .padding(defaultPadding),
                    painter = painterResource(id = R.drawable.ic_baseline_close_24),
                    contentDescription = stringDictionary.clearTextButton,
                    colorFilter = ColorFilter.tint(Color.Black)
                )
            }
        },
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClick()
            }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun SearchTextFieldPreview() {
    SearchTextField(
        textInput = "",
        hint = "hello world",
        onTextInputChange = {},
        onSearchClick = {},
        onClickErase = {},
    )
}