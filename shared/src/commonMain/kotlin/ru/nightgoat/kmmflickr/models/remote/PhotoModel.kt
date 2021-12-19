package ru.nightgoat.kmmflickr.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.nightgoat.kmmflickr.core.base.IConvertible
import ru.nightgoat.kmmflickr.models.ui.PhotoUi
import ru.nightgoat.kmmflickr.models.util.Url

@Serializable
data class PhotoModel(
    @SerialName("farm")
    val farm: Int? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("isfamily")
    val isFamily: Int? = null,
    @SerialName("isfriend")
    val isFriend: Int? = null,
    @SerialName("ispublic")
    val isPublic: Int? = null,
    @SerialName("owner")
    val owner: String? = null,
    @SerialName("secret")
    val secret: String? = null,
    @SerialName("server")
    val server: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("url_m")
    val url: String? = null,
    @SerialName("height_m")
    val height: Int? = null,
    @SerialName("width_m")
    val width: Int? = null
) : IConvertible<PhotoUi> {

    /** we dont need models without url for our gallery*/
    override fun convert() = url?.run {
        PhotoUi(
            model = this@PhotoModel,
            url = Url(this),
            description = title.orEmpty(),
            width = width ?: 0,
            height = height ?: 0
        )
    }
}