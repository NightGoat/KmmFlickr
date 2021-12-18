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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import ru.nightgoat.kmmflickr.android.R


@Composable
fun VerticalGallery(urls: List<String>, isTest: Boolean = false) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(urls) { url ->
            GalleryImage(url, isTest)
        }
    }
}

// TODO: 18.12.2021 change url to object that contains url and description
@OptIn(ExperimentalCoilApi::class)
@Composable
fun GalleryImage(url: String, isTest: Boolean) {
    if (isTest) {
        Image(
            painter = painterResource(id = R.drawable.court),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            contentScale = ContentScale.FillWidth,
        )
    } else {
        val painter =
            rememberImagePainter(
                data = url,
                builder = {
                    size(OriginalSize)
                    placeholder(R.drawable.court)
                },
            )
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .then(
                    (painter.state as? ImagePainter.State.Success)
                        ?.painter
                        ?.intrinsicSize
                        ?.let { intrinsicSize ->
                            Modifier.aspectRatio(intrinsicSize.width / intrinsicSize.height)
                        } ?: Modifier
                ),
            painter = painter,
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
        )
    }
}