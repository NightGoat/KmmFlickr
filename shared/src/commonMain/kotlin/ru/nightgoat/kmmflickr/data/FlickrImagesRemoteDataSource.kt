package ru.nightgoat.kmmflickr.data

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import ru.nightgoat.kmmflickr.core.IRemoteDataSource
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

    override suspend fun getPhotos(tag: String): FlickrImageModel {
        return httpClient.get("https://api.flickr.com/services/rest?") {
            parameter("api_key", "98c37f47eba0d8dd2db12c2207eb39c8")
            parameter("method", "flickr.photos.search")
            parameter("tags", tag)
            parameter("format", "json")
            parameter("nojsoncallback", "true")
            parameter("callback", "true")
            parameter("extras", "media")
            parameter("extras", "url_sq")
            parameter("extras", "url_m")
            parameter("per_page", "20")
            parameter("page", "1")
        }
    }
}

interface ImagesRemoteDataSource : IRemoteDataSource {
    suspend fun getPhotos(tag: String = "Electrolux"): FlickrImageModel
}