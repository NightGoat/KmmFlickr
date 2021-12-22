package ru.nightgoat.kmmflickr.android.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import ru.nightgoat.kmmflickr.android.presentation.theme.defaultMediumPadding
import ru.nightgoat.kmmflickr.android.presentation.theme.defaultPadding
import ru.nightgoat.kmmflickr.models.ui.PhotoUi
import ru.nightgoat.kmmflickr.providers.strings.stringDictionary


@Composable
fun VerticalGallery(urls: List<PhotoUi>, onPhotoClick: (String) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(urls) { url ->
            GalleryImage(url, onPhotoClick)
        }
    }
}

@Composable
fun GalleryImage(photo: PhotoUi, onPhotoClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(bottom = defaultMediumPadding)
            .clickable {
                onPhotoClick(photo.id)
            }
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth(),
                model = photo.url.link,
                contentDescription = photo.description,
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(defaultMediumPadding)
                    )
                },
                error = {
                    Text(
                        modifier = Modifier.padding(defaultMediumPadding),
                        text = stringDictionary.errorLoadingImage
                    )
                },
                contentScale = ContentScale.FillWidth
            )
            Text(
                modifier = Modifier.padding(defaultPadding),
                text = photo.description
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GalleryImagePreview() {
    GalleryImage(photo = PhotoUi.fake, onPhotoClick = {}
    )
}