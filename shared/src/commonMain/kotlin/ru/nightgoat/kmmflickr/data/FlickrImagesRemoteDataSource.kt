package ru.nightgoat.kmmflickr.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import ru.nightgoat.kmmflickr.core.base.IRemoteDataSource
import ru.nightgoat.kmmflickr.core.constants.FlickrApiConstants
import ru.nightgoat.kmmflickr.core.extensions.parametersOf
import ru.nightgoat.kmmflickr.models.remote.FlickrImageModel
import ru.nightgoat.kmmflickr.models.ui.PhotoUi
import ru.nightgoat.kmmflickr.providers.strings.stringDictionary

class FlickrImagesRemoteDataSource(
    private val httpClient: HttpClient
) : ImagesRemoteDataSource {

    override suspend fun getPhotos(
        tag: String,
        pages: Int,
        page: Int
    ): FlickrImageModel {
        return if (tag.isNotEmpty()) {
            with(FlickrApiConstants) {
                httpClient.get(BASE_URL) {
                    parametersOf(
                        API_KEY_PARAM to API_KEY,
                        METHOD_PARAM to METHOD_VALUE,
                        TAGS_PARAM to tag,
                        FORMAT_PARAM to FORMAT_VALUE,
                        NO_JSON_CALLBACK_PARAM to true.toString(),
                        CALLBACK_PARAM to true.toString(),
                        EXTRAS_PARAM to MEDIA_EXTRAS_VALUE,
                        EXTRAS_PARAM to URL_SQ_EXTRAS_VALUE,
                        EXTRAS_PARAM to URL_M_EXTRAS_VALUE,
                        PER_PAGE_PARAM to pages.toString(),
                        PAGE_PARAM to page.toString()
                    )
                }
            }
        } else {
            throw IllegalArgumentException(stringDictionary.pleaseEnterSomething)
        }
    }

    override suspend fun downloadPhoto(photo: PhotoUi): ByteArray {
        val response: HttpResponse = httpClient.get(photo.url.link)
        return response.receive()
    }
}

/** Data source that make http calls */
interface ImagesRemoteDataSource : IRemoteDataSource {

    /** calls api and returns raw data json*/
    @Throws(Exception::class)
    suspend fun getPhotos(
        tag: String,
        pages: Int = DEFAULT_PAGES_QUANTITY,
        page: Int = DEFAULT_PAGE
    ): FlickrImageModel

    @Throws(Exception::class)
    suspend fun downloadPhoto(photo: PhotoUi): ByteArray

    companion object {
        const val DEFAULT_PAGES_QUANTITY = 21
        const val DEFAULT_PAGE = 1
    }
}