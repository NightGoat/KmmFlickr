package ru.nightgoat.kmmflickr.core.extensions

import io.ktor.client.request.*

/**
 * For simpler http parameters building. Works like bundleOf in Android. Example:
 * httpClient.get(BASE_URL) {
 *      parametersOf(
 *          API_KEY_PARAM to API_KEY,
 *          METHOD_PARAM to METHOD_VALUE,
 *          TAGS_PARAM to tag
 *      )
 * }
 * */
fun HttpRequestBuilder.parametersOf(vararg pairs: Pair<String, Any?>) {
    pairs.forEach { pair ->
        pair.second?.let { url.parameters.append(pair.first, it.toString()) }
    }
}
