package ru.nightgoat.kmmflickr.data

import ru.nightgoat.kmmflickr.core.IRemoteRepository
import ru.nightgoat.kmmflickr.core.convert
import ru.nightgoat.kmmflickr.models.ui.PhotoUi

class ImagesRepository(override val remoteDataSource: ImagesRemoteDataSource) :
    IImagesRepository {
    override suspend fun loadImages(tag: String): Result<List<PhotoUi>> {
        return kotlin.runCatching {
            val raw = remoteDataSource.getPhotos(tag)
            val converted = raw.photos?.photos.convert()
            converted
        }
    }
}

interface IImagesRepository : IRemoteRepository<ImagesRemoteDataSource> {
    suspend fun loadImages(tag: String): Result<List<PhotoUi>>
}