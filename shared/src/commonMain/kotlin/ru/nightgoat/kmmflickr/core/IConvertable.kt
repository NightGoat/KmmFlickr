package ru.nightgoat.kmmflickr.core

interface IConvertable<T> {
    fun convert(): T?
}

fun <T> List<IConvertable<T>>?.convert(): List<T> {
    return this?.mapNotNull {
        it.convert()
    }.orEmpty()
}