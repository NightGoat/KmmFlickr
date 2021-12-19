package ru.nightgoat.kmmflickr.android.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import ru.nightgoat.kmmflickr.android.R
import ru.nightgoat.kmmflickr.models.ui.PhotoUi


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
            val painter =
                rememberImagePainter(
                    data = photo.url.link,
                    builder = {
                        placeholder(R.drawable.image_placeholder)
                    },
                )
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(photo.aspectRatio),
                painter = painter,
                contentScale = ContentScale.FillWidth,
                contentDescription = photo.description,
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