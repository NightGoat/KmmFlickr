package ru.nightgoat.kmmflickr

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.ktor.client.engine.mock.*
import io.ktor.client.features.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.nightgoat.kmmflickr.data.FlickrImagesRemoteDataSource
import ru.nightgoat.kmmflickr.data.ImagesRemoteDataSource
import ru.nightgoat.kmmflickr.models.ui.PhotoUi
import ru.nightgoat.kmmflickr.providers.http.HttpClientProvider
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CommonTests {

    private lateinit var okSource: ImagesRemoteDataSource

    @BeforeTest
    fun initSource() {
        okSource = getDataSource(NORMAL_RESPONSE)
    }

    private fun getDataSource(
        json: String,
        status: HttpStatusCode = HttpStatusCode.OK
    ): ImagesRemoteDataSource {
        val engine = MockEngine {
            respond(
                content = ByteReadChannel(json),
                status = status,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client =
            HttpClientProvider.provideJsonClient(loggingSettings = null, customEngine = engine)
        return FlickrImagesRemoteDataSource(client)
    }

    @Test
    fun dataSource_test_getPhotos_EMPTY_TAG_EXCEPTION() {
        runTest {
            shouldThrow<IllegalArgumentException> { okSource.getPhotos("") }
        }
    }

    @Test
    fun dataSource_test_getPhotos_EMPTY_TAG_NO_EXCEPTION() {
        runTest {
            shouldNotThrow<IllegalArgumentException> { okSource.getPhotos(tag) }
        }
    }

    @Test
    fun dataSource_test_getPhotos_OK_ANSWER_NOT_NULL() {
        runTest {
            val response = okSource.getPhotos(tag)
            response.photos?.photos?.firstOrNull() shouldNotBe null
        }
    }

    @Test
    fun dataSource_test_getPhotos_OK_ANSWER_EQUALS() {
        runTest {
            val response = okSource.getPhotos(tag)
            response.photos?.photos?.firstOrNull()?.id shouldBe "51713985825"
        }
    }

    @Test
    fun dataSource_test_getPhotos_NOT_FOUND_STATUS_THROWS() {
        runTest {
            val badDataSource = getDataSource(EMPTY_RESPONSE, NOT_FOUND_STATUS)
            shouldThrow<ClientRequestException> { badDataSource.getPhotos(tag) }
        }
    }

    @Test
    fun dataSource_test_getPhotos_EMPTY_RESPONSE() {
        runTest {
            val badDataSource = getDataSource(EMPTY_RESPONSE)
            shouldThrow<RuntimeException> { badDataSource.getPhotos(tag) }
        }
    }

    @Test
    fun dataSource_test_getPhotos_EMPTY_RESPONSE_2() {
        runTest {
            val badDataSource = getDataSource(EMPTY_JSON_RESPONSE)
            val response = badDataSource.getPhotos(tag)
            response.stat shouldBe null
        }
    }

    @Test
    fun dataSource_test_downloadPhoto_NOT_EMPTY() {
        runTest {
            val badDataSource = getDataSource(EMPTY_JSON_RESPONSE)
            val response = badDataSource.downloadPhotoByteArray(PhotoUi.fake)
            response.isNotEmpty() shouldBe true
        }
    }

    companion object {
        const val tag = "Electrolux"
        val NOT_FOUND_STATUS = HttpStatusCode.NotFound

        val NORMAL_RESPONSE =
            """{"photos":{"page":1,"pages":462,"perpage":20,"total":9234,"photo":[{"id":"51713985825","owner":"143425468@N05","secret":"77458dcabb","server":"65535","farm":66,"title":"Servel Electrolux Gas Refrigerator, 1940 ad","ispublic":1,"isfriend":0,"isfamily":0,"url_m":"https:\/\/live.staticflickr.com\/65535\/51713985825_77458dcabb.jpg","height_m":500,"width_m":386}]},"stat":"ok"}""".trimIndent()
        const val EMPTY_RESPONSE = ""
        const val EMPTY_JSON_RESPONSE = "{}"
    }
}