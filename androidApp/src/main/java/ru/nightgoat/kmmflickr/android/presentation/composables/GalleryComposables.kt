package ru.nightgoat.kmmflickr.android.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
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

@OptIn(ExperimentalCoilApi::class)
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
                        diskCachePolicy(CachePolicy.ENABLED)
                        crossfade(true)
                    }
                )
            when (painter.state) {
                is ImagePainter.State.Empty, is ImagePainter.State.Success -> { //We need call image composable in Empty state to start its downloading
                    CoilImage(photo, painter)
                }
                is ImagePainter.State.Loading -> {
                    PlaceHolderBox(aspectRatio = photo.aspectRatio) {
                        CircularProgressIndicator()
                    }
                }
                is ImagePainter.State.Error -> {
                    PlaceHolderBox(aspectRatio = photo.aspectRatio) {
                        Text(
                            modifier = Modifier.padding(defaultPadding),
                            text = stringDictionary.errorLoadingImage
                        )
                    }
                }
            }
            ImagesBottomDescription(photo)
        }
    }
}

@Composable
private fun ImagesBottomDescription(photo: PhotoUi) {
    Text(
        modifier = Modifier.padding(defaultPadding),
        text = photo.description
    )
}

@Composable
private fun CoilImage(
    photo: PhotoUi,
    painter: ImagePainter
) {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(photo.aspectRatio),
        painter = painter,
        contentScale = ContentScale.FillWidth,
        contentDescription = photo.description,
    )
}

@Composable
fun PlaceHolderBox(
    aspectRatio: Float,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatio),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun GalleryImagePreview() {
    GalleryImage(photo = PhotoUi.fake, onPhotoClick = {})
}