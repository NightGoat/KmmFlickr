package ru.nightgoat.kmmflickr.core.constants

import ru.nightgoat.kmmflickr.core.constants.MimeTypesConstants.JPEG_MIME_VALUE

enum class MimeTypes(val value: String) {
    JPEG(JPEG_MIME_VALUE);
}

private object MimeTypesConstants {
    const val JPEG_MIME_VALUE = "image/jpeg"
}