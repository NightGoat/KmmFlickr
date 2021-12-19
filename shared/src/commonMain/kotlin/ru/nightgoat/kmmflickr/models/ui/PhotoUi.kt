package ru.nightgoat.kmmflickr.models.ui

import ru.nightgoat.kmmflickr.models.remote.PhotoModel
import ru.nightgoat.kmmflickr.models.util.Url

data class PhotoUi(
    private val model: PhotoModel,
    val url: Url,
    val description: String,
    val width: Int,
    val height: Int
) {
    val id
        get() = model.id.orEmpty()

    val aspectRatio: Float
        get() {
            return if (height != 0) {
                width.toFloat() / height
            } else {
                DEFAULT_ASPECT_RATIO
            }
        }

    companion object {
        const val DEFAULT_ASPECT_RATIO = 0.75f
    }
}
