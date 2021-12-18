package ru.nightgoat.kmmflickr.models.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotosModel(
    @SerialName("page")
    val page: Int? = null,
    @SerialName("pages")
    val pages: Int? = null,
    @SerialName("perpage")
    val perPage: Int? = null,
    @SerialName("photo")
    val photos: List<PhotoModel>? = null,
    @SerialName("total")
    val total: Int? = null
)