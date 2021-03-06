package ru.nightgoat.kmmflickr.providers.http

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.serialization.json.Json
import ru.nightgoat.kmmflickr.initLogger

internal object HttpClientProvider {

    fun provideJsonClient(
        loggingSettings: LoggingSettings? = LoggingSettings(
            logLevel = LogLevel.ALL,
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.i(tag = "FlickrImagesRemoteDataSource", message = message)
                }
            }
        ),
        jsonSerializer: () -> JsonSerializer = {
            val json = Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            }
            KotlinxSerializer(json)
        },
        customEngine: HttpClientEngine? = null
    ): HttpClient {
        val settings: HttpClientConfig<*>.() -> Unit = {
            loggingSettings?.let { settings ->
                install(Logging) {
                    level = settings.logLevel
                    logger = settings.logger
                }
            }
            install(JsonFeature) {
                serializer = jsonSerializer()
            }
            initLogger()
        }

        return customEngine?.let { engine ->
            HttpClient(engine = engine, block = settings)
        } ?: HttpClient(block = settings)
    }
}

data class LoggingSettings(
    val logLevel: LogLevel,
    val logger: Logger
)