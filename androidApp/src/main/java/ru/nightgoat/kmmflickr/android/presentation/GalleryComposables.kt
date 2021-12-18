package ru.nightgoat.kmmflickr.android.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import ru.nightgoat.kmmflickr.android.R
import ru.nightgoat.kmmflickr.models.ui.PhotoUi


@Composable
fun VerticalGallery(urls: List<PhotoUi>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(urls) { url ->
            GalleryImage(url)
        }
    }
}

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
            .aspectRatio(photo.aspectRatio)
            .clickable {
                // TODO: 18.12.2021
            }
            .padding(bottom = defaultPadding),
        painter = painter,
        contentScale = ContentScale.FillWidth,
        contentDescription = photo.description,
    )
}