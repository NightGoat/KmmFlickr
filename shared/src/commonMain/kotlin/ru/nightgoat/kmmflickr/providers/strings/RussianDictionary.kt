package ru.nightgoat.kmmflickr.providers.strings

object RussianDictionary : IDictionary {
    override val searchHint: String by lazy {
        "Введите поисковой запрос"
    }
    override val saveImageQuestion: String by lazy {
        "Сохранить изображение?"
    }
    override val yes: String by lazy {
        "Да"
    }
    override val no: String by lazy {
        "Нет"
    }
    override val retry: String by lazy {
        "Повторить"
    }
    override val imageSavedSuccessfully: String by lazy {
        "Изображение успешно сохранено"
    }
    override val imageSaveError: String by lazy {
        "Произошла ошибка во премя сохранения изображения"
    }
    override val searchIcon: String by lazy {
        "Иконка поиска"
    }
    override val clearTextButton: String by lazy {
        "Кнопка очистки текста"
    }
}