package ru.nightgoat.kmmflickr.android.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.nightgoat.kmmflickr.android.R
import ru.nightgoat.kmmflickr.android.presentation.theme.FlickrTheme
import ru.nightgoat.kmmflickr.android.presentation.theme.defaultButtonSize
import ru.nightgoat.kmmflickr.android.presentation.theme.defaultPadding
import ru.nightgoat.kmmflickr.android.presentation.util.ImageData
import ru.nightgoat.kmmflickr.models.ui.PhotoUi
import ru.nightgoat.kmmflickr.providers.strings.stringDictionary

@Composable
fun ImageDescription(
    photoUi: PhotoUi,
    onCancelSave: () -> Unit,
    onSaveClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancelSave,
        buttons = {
            DescriptionButtons(onCancelSave, onSaveClick)
        },
        title = {
            Text(photoUi.description)
        },
        text = {
            DescriptionBody(photoUi)
        },
    )
}

/** Buttons Back and Download Image */
@Composable
private fun DescriptionButtons(onCancelSave: () -> Unit, onSaveClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(defaultPadding)
    ) {
        Button(
            modifier = Modifier
                .size(defaultButtonSize)
                .weight(1f)
                .padding(end = defaultPadding),
            onClick = onCancelSave
        ) {
            ButtonText(stringDictionary.back)
        }
        Button(
            modifier = Modifier
                .weight(1f)
                .size(defaultButtonSize),
            onClick = onSaveClick
        ) {
            ButtonText(stringDictionary.download)
        }
    }
}

/** Icon and description rows (owner, height, width)  */
@Composable
private fun DescriptionBody(photoUi: PhotoUi) {
    val imageAdditionalString = "px"
    Column {
        DescriptionRow(
            text = photoUi.owner,
            imageData = ImageData(
                drawableResId = R.drawable.ic_baseline_person_24,
                description = stringDictionary.owner,
            )
        )
        DescriptionRow(
            text = "${photoUi.height}$imageAdditionalString",
            imageData = ImageData(
                drawableResId = R.drawable.ic_baseline_height_24,
                description = stringDictionary.height,
            )
        )
        DescriptionRow(
            text = "${photoUi.width}$imageAdditionalString",
            imageData = ImageData(
                drawableResId = R.drawable.ic_baseline_settings_ethernet_24,
                description = stringDictionary.width,
            )
        )
    }
}

@Composable
private fun DescriptionRow(text: String, imageData: ImageData) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imageData.drawableResId),
            contentDescription = imageData.description,
            colorFilter = ColorFilter.tint(imageData.color)
        )
        SimpleSpacer()
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun DescriptionBodyPr() {
    FlickrTheme {
        DescriptionBody(PhotoUi.fake)
    }
}

@Preview(showBackground = true)
@Composable
fun DescriptionButtonsPr() {
    FlickrTheme {
        DescriptionButtons({}, {})
    }
}

@Preview(showBackground = true)
@Composable
fun DescriptionRowPr() {
    FlickrTheme {
        DescriptionRow(
            text = "Hello World",
            imageData = ImageData(R.drawable.ic_baseline_person_24)
        )
    }
}