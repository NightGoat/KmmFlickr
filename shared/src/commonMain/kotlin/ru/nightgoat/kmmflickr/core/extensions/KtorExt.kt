package ru.nightgoat.kmmflickr.core.extensions

import io.ktor.client.request.*

fun HttpRequestBuilder.parametersOf(vararg pairs: Pair<String, Any?>) {
    pairs.forEach { pair ->
        pair.second?.let { url.parameters.append(pair.first, it.toString()) }
    }
}
