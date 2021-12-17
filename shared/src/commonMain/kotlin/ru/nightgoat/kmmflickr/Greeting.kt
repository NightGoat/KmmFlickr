package ru.nightgoat.kmmflickr

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class Greeting {
    private val httpClient = HttpClient()

    private suspend fun getPhotos(request: String = "Electrolux"): String {
        val response: HttpResponse = httpClient.get("https://api.flickr.com/services/rest?") {
            parameter("api_key", "98c37f47eba0d8dd2db12c2207eb39c8")
            parameter("method", "flickr.photos.search")
            parameter("tags", request)
            parameter("format", "json")
            parameter("callback", "true")
            parametersOf("extras", listOf("media", "url_sq", "url_m"))
            parameter("per_page", "20")
            parameter("page", "1")
        }
        return response.readText()
    }

    suspend fun greeting(): String {
        return getPhotos()
    }
}