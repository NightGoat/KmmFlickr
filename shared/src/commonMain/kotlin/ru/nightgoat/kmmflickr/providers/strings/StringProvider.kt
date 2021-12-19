package ru.nightgoat.kmmflickr.providers.strings

import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object StringProvider {
    private const val RUSSIAN_LOCALE_KEY = "ru"

    var currentLanguage: String = "en"

    fun initWithLanguage(language: String) {
        currentLanguage = language
    }

    fun getLocale(): IDictionary {
        return when (currentLanguage) {
            RUSSIAN_LOCALE_KEY -> RussianDictionary
            else -> EnglishDictionary
        }
    }
}