package ru.nightgoat.kmmflickr.data

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import ru.nightgoat.kmmflickr.core.base.IRemoteDataSource
import ru.nightgoat.kmmflickr.core.constants.FlickrApiConstants.API_KEY
import ru.nightgoat.kmmflickr.core.constants.FlickrApiConstants.API_KEY_PARAM
import ru.nightgoat.kmmflickr.core.constants.FlickrApiConstants.BASE_URL
import ru.nightgoat.kmmflickr.core.constants.FlickrApiConstants.CALLBACK_PARAM
import ru.nightgoat.kmmflickr.core.constants.FlickrApiConstants.EXTRAS_PARAM
import ru.nightgoat.kmmflickr.core.constants.FlickrApiConstants.FORMAT_PARAM
import ru.nightgoat.kmmflickr.core.constants.FlickrApiConstants.FORMAT_VALUE
import ru.nightgoat.kmmflickr.core.constants.FlickrApiConstants.MEDIA_EXTRAS_VALUE
import ru.nightgoat.kmmflickr.core.constants.FlickrApiConstants.METHOD_PARAM
import ru.nightgoat.kmmflickr.core.constants.FlickrApiConstants.METHOD_VALUE
import ru.nightgoat.kmmflickr.core.constants.FlickrApiConstants.NO_JSON_CALLBACK_PARAM
import ru.nightgoat.kmmflickr.core.constants.FlickrApiConstants.PAGE_PARAM
import ru.nightgoat.kmmflickr.core.constants.FlickrApiConstants.PER_PAGE_PARAM
import ru.nightgoat.kmmflickr.core.constants.FlickrApiConstants.TAGS_PARAM
import ru.nightgoat.kmmflickr.core.constants.FlickrApiConstants.URL_M_EXTRAS_VALUE
import ru.nightgoat.kmmflickr.core.constants.FlickrApiConstants.URL_SQ_EXTRAS_VALUE
import ru.nightgoat.kmmflickr.core.extensions.parametersOf
import ru.nightgoat.kmmflickr.initLogger
import ru.nightgoat.kmmflickr.models.remote.FlickrImageModel

class FlickrImagesRemoteDataSource : ImagesRemoteDataSource {
    private val httpClient = HttpClient {
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.i(tag = "FlickrImagesRemoteDataSource", message = message)
                }
            }
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }.also { initLogger() }

    override suspend fun getPhotos(
        tag: String,
        pages: Int,
        page: Int
    ): FlickrImageModel {
        return httpClient.get(BASE_URL) {
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
}

interface ImagesRemoteDataSource : IRemoteDataSource {
    suspend fun getPhotos(
        tag: String,
        pages: Int = DEFAULT_PAGES_QUANTITY,
        page: Int = DEFAULT_PAGE
    ): FlickrImageModel

    companion object {
        const val DEFAULT_PAGES_QUANTITY = 20
        const val DEFAULT_PAGE = 1
    }
}