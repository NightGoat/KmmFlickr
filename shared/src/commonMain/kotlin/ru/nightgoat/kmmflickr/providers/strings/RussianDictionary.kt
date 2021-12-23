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
    override val download: String by lazy {
        "Скачать"
    }
    override val back: String by lazy {
        "Назад"
    }
    override val owner: String by lazy {
        "Владелец"
    }
    override val height: String by lazy {
        "Высота"
    }
    override val width: String by lazy {
        "Ширина"
    }
    override val pleaseEnterSomething: String by lazy {
        "Пожалуйста введите что-нибудь"
    }
    override val nothingFound: String by lazy {
        "Ничего не найдено"
    }
    override val errorLoadingImage: String by lazy {
        "Произошла ошибка во время загрузки изображения"
    }
    override val serverError: String by lazy {
        "Ошибка сервера"
    }
}