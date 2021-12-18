package ru.nightgoat.kmmflickr.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.nightgoat.kmmflickr.core.IConvertable
import ru.nightgoat.kmmflickr.models.ui.PhotoUi
import ru.nightgoat.kmmflickr.models.util.Url

@Serializable
data class PhotoModel(
    @SerialName("farm")
    val farm: Int?,
    @SerialName("id")
    val id: String?,
    @SerialName("isfamily")
    val isFamily: Int?,
    @SerialName("isfriend")
    val isFriend: Int?,
    @SerialName("ispublic")
    val isPublic: Int?,
    @SerialName("owner")
    val owner: String?,
    @SerialName("secret")
    val secret: String?,
    @SerialName("server")
    val server: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("url_m")
    val url: String? = null,
    @SerialName("height_m")
    val height: Int? = null,
    @SerialName("width_m")
    val width: Int? = null
) : IConvertable<PhotoUi> {
    override fun convert() = url?.run {
        PhotoUi(
            url = Url(this),
            title = title.orEmpty(),
            width = width ?: 0,
            height = height ?: 0
        )
    }
}