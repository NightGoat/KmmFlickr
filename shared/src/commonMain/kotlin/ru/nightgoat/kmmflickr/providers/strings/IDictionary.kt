package ru.nightgoat.kmmflickr.providers.strings

/**
 * Base string dictionary. Every new string resource must be written there first.
 * */
interface IDictionary {
    //Common
    /** @see EnglishDictionary.searchHint */
    val searchHint: String

    /** @see EnglishDictionary.yes */
    val yes: String

    /** @see EnglishDictionary.no */
    val no: String

    /** @see EnglishDictionary.retry */
    val retry: String

    /** @see EnglishDictionary.searchIcon */
    val searchIcon: String

    /** @see EnglishDictionary.clearTextButton */
    val clearTextButton: String

    /** @see EnglishDictionary.download */
    val download: String

    /** @see EnglishDictionary.back */
    val back: String

    /** @see EnglishDictionary.owner */
    val owner: String

    /** @see EnglishDictionary.height */
    val height: String

    /** @see EnglishDictionary.width */
    val width: String

    //Alerts
    /** @see EnglishDictionary.saveImageQuestion */
    val saveImageQuestion: String

    /** @see EnglishDictionary.imageSavedSuccessfully */
    val imageSavedSuccessfully: String

    //Errors
    /** @see EnglishDictionary.pleaseEnterSomething */
    val pleaseEnterSomething: String

    /** @see EnglishDictionary.nothingFound */
    val nothingFound: String

    /** @see EnglishDictionary.errorLoadingImage */
    val errorLoadingImage: String

    /** @see EnglishDictionary.imageSaveError */
    val imageSaveError: String
}