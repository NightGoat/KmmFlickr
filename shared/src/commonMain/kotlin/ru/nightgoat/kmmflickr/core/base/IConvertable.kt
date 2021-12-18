package ru.nightgoat.kmmflickr.core.base

interface IConvertable<T> {
    fun convert(): T?
}

fun <T> List<IConvertable<T>>?.convert(): List<T> {
    return this?.mapNotNull {
        it.convert()
    }.orEmpty()
}