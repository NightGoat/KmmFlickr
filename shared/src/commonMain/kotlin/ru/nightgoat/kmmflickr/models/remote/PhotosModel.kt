package ru.nightgoat.kmmflickr.models.remote


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotosModel(
    @SerialName("page")
    val page: Int?,
    @SerialName("pages")
    val pages: Int?,
    @SerialName("perpage")
    val perPage: Int?,
    @SerialName("photo")
    val photos: List<PhotoModel>?,
    @SerialName("total")
    val total: Int?
)