package ru.nightgoat.kmmflickr.core.constants

import ru.nightgoat.kmmflickr.core.constants.MimeTypesConstants.JPEG_EXTENSION
import ru.nightgoat.kmmflickr.core.constants.MimeTypesConstants.JPEG_MIME_VALUE

enum class MimeTypes(val value: String, val extension: String) {
    JPEG(JPEG_MIME_VALUE, JPEG_EXTENSION);
}

private object MimeTypesConstants {
    const val JPEG_MIME_VALUE = "image/jpeg"
    const val JPEG_EXTENSION = ".jpg"
}