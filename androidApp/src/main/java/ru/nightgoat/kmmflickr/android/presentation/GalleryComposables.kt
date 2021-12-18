package ru.nightgoat.kmmflickr.android.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import ru.nightgoat.kmmflickr.android.R
import ru.nightgoat.kmmflickr.models.ui.PhotoUi


@Composable
fun VerticalGallery(urls: List<PhotoUi>, isTest: Boolean = false) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(urls) { url ->
            GalleryImage(url)
        }
    }
}

// TODO: 18.12.2021 change url to object that contains url and description
@OptIn(ExperimentalCoilApi::class)
@Composable
fun GalleryImage(photo: PhotoUi) {
    val painter =
        rememberImagePainter(
            data = photo.url.link,
            builder = {
                size(OriginalSize)
                placeholder(R.drawable.image_placeholder)
            },
        )
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(
                photo.aspectRatio
            )
            .padding(bottom = 8.dp),
        painter = painter,
        contentScale = ContentScale.FillWidth,
        contentDescription = null,
    )
}