package ru.nightgoat.kmmflickr.core

interface IUseCase {

    interface Out<T> {
        suspend operator fun invoke(): T
    }

    interface InOut<T, S> {
        suspend operator fun invoke(param: T): S
    }
}