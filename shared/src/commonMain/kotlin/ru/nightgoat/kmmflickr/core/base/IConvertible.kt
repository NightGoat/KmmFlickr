package ru.nightgoat.kmmflickr.core.base

/** interface for easier mapping.
 * @see mapConvertibles() */
interface IConvertible<T> {
    fun convert(): T?
}

fun <T> List<IConvertible<T>>?.mapConvertibles(): List<T> {
    return this?.mapNotNull {
        it.convert()
    }.orEmpty()
}