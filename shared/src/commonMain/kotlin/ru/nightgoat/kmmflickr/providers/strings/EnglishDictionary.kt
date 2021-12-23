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
    override val searchIcon: String by lazy {
        "Search icon"
    }
    override val clearTextButton: String by lazy {
        "Clear text button"
    }
    override val download: String by lazy {
        "Download"
    }
    override val back: String by lazy {
        "Back"
    }
    override val owner: String by lazy {
        "Owner"
    }
    override val height: String by lazy {
        "Height"
    }
    override val width: String by lazy {
        "Width"
    }
    override val pleaseEnterSomething: String by lazy {
        "Please enter something"
    }
    override val nothingFound: String by lazy {
        "Nothing found!"
    }
    override val errorLoadingImage: String by lazy {
        "Error loading image"
    }
    override val serverError: String by lazy {
        "Server error"
    }
}