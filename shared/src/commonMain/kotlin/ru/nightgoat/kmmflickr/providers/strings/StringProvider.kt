package ru.nightgoat.kmmflickr.providers.strings

import kotlin.native.concurrent.ThreadLocal

val stringDictionary
    get() = StringProvider.dictionary

/**
 * Base factory for IDictionary classes.
 * initWithLanguage must be called on launch and every configuration change (android)
 * In android current language you can get from: Locale.getDefault().language
 * */
@ThreadLocal
object StringProvider {
    private const val RUSSIAN_LOCALE_KEY = "ru"
    private const val ENGLISH_LOCALE_KEY = "en"

    var currentLanguage: String = ENGLISH_LOCALE_KEY
    val dictionary: IDictionary
        get() = when (currentLanguage) {
            RUSSIAN_LOCALE_KEY -> RussianDictionary
            else -> EnglishDictionary
        }

    fun initWithLanguage(language: String) {
        currentLanguage = language
    }
}