package ru.nightgoat.kmmflickr

expect class Platform() {
    val platform: String
}

expect fun initLogger()