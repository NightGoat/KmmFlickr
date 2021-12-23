package ru.nightgoat.kmmflickr.models.ui

import ru.nightgoat.kmmflickr.models.remote.PhotoModel
import ru.nightgoat.kmmflickr.models.util.Url

data class PhotoUi(
    private val model: PhotoModel,
    val url: Url,
    val description: String,
    val width: Int,
    val height: Int,
    val owner: String
) {
    val id
        get() = model.id.orEmpty()

    /** calculated image's aspect ratio. If height is == 0, returns 3 : 4 */
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
        val fake = PhotoUi(
            model = PhotoModel(
                id = "123"
            ),
            url = Url(link = "http://example.com/123.jpg"),
            description = "Hello world",
            width = 100,
            height = 2000,
            owner = "No one"
        )
    }
}
