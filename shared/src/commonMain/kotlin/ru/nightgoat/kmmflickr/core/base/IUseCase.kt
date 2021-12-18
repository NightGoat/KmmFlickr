package ru.nightgoat.kmmflickr.core.base

interface IUseCase {

    interface Out<T> {
        suspend operator fun invoke(): T
    }

    interface InOut<T, S> {
        suspend operator fun invoke(param: T): S
    }
}