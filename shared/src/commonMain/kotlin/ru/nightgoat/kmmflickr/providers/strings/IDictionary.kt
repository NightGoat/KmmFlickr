package ru.nightgoat.kmmflickr.providers.strings

/**
 * Base string dictionary. Every new string resource must be written there first.
 * */
interface IDictionary {
    val searchHint: String
    val saveImageQuestion: String
    val yes: String
    val no: String
    val retry: String
    val imageSavedSuccessfully: String
    val imageSaveError: String
}