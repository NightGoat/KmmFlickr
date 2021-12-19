package ru.nightgoat.kmmflickr.providers.strings

object EnglishDictionary : IDictionary {
    override val searchHint: String by lazy {
        "Enter search query"
    }
    override val saveImageQuestion: String by lazy {
        "Do you want to save image?"
    }
    override val yes: String by lazy {
        "Yes"
    }
    override val no: String by lazy {
        "No"
    }
    override val retry: String by lazy {
        "Retry"
    }
    override val imageSavedSuccessfully: String by lazy {
        "Image saved successfully"
    }
    override val imageSaveError: String by lazy {
        "An error occurred while saving the image"
    }
}