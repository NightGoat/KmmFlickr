package ru.nightgoat.kmmflickr.android.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SnackBarWithAction(
    text: String,
    actionText: String,
    onActionClick: () -> Unit,
) {
    Snackbar(
        modifier = Modifier.padding(defaultPadding),
        action = {
            Text(text = actionText.uppercase(),
                modifier = Modifier
                    .clickable {
                        onActionClick()
                    }
                    .padding(defaultPadding),
                color = MaterialTheme.colors.primaryVariant)
        }
    ) {
        Text(text)
    }
}

@Composable
fun SnackBarWithActionOnBottom(
    text: String,
    actionText: String,
    onActionClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        SnackBarWithAction(text, actionText, onActionClick)
    }
}

@Preview(showBackground = true)
@Composable
private fun SnackBarWithActionOnBottomP() {

    SnackBarWithActionOnBottom("Test", "YES") {

    }

}