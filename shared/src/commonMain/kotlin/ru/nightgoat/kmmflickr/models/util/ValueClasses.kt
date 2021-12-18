package ru.nightgoat.kmmflickr.models.util

import kotlin.jvm.JvmInline

@JvmInline
value class Url(val link: String) {
    val isEmpty: Boolean
        get() = link.isEmpty()
}