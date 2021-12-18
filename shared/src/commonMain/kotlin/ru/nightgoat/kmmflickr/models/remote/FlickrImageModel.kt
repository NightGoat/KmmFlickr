package ru.nightgoat.kmmflickr.models.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlickrImageModel(
    @SerialName("photos")
    val photos: PhotosModel? = null,
    @SerialName("stat")
    val stat: String? = null
)