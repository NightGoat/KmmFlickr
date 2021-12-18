package ru.nightgoat.kmmflickr.core.base

interface IRemoteRepository<T : IRemoteDataSource> {
    val remoteDataSource: T
}