package ru.nightgoat.kmmflickr.core

interface IRemoteRepository<T : IRemoteDataSource> {
    val remoteDataSource: T
}