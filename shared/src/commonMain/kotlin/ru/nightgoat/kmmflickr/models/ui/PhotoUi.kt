package ru.nightgoat.kmmflickr.models.ui

import ru.nightgoat.kmmflickr.models.util.Url

data class PhotoUi(
    val url: Url,
    val title: String,
    val width: Int,
    val height: Int
) {
    val aspectRatio: Float
        get() {
            return if (height != 0) {
                width.toFloat() / height
            } else {
                3.toFloat() / 4
            }
        }

}
