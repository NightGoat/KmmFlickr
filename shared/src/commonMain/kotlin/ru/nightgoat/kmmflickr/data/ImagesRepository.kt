package ru.nightgoat.kmmflickr.data

import ru.nightgoat.kmmflickr.core.base.IRemoteRepository
import ru.nightgoat.kmmflickr.core.base.convert
import ru.nightgoat.kmmflickr.models.ui.PhotoUi

class ImagesRepository(
    override val remoteDataSource: ImagesRemoteDataSource
) :
    IImagesRepository {
    override suspend fun loadImages(tag: String): Result<List<PhotoUi>> {
        return kotlin.runCatching {
            val raw = remoteDataSource.getPhotos(tag)
            raw.photos?.photos.convert()
        }
    }

    override suspend fun downloadPhoto(photoUi: PhotoUi): Result<ByteArray> {
        return kotlin.runCatching {
            remoteDataSource.downloadPhoto(photoUi)
        }
    }

}

interface IImagesRepository : IRemoteRepository<ImagesRemoteDataSource> {
    suspend fun loadImages(tag: String): Result<List<PhotoUi>>
    suspend fun downloadPhoto(photoUi: PhotoUi): Result<ByteArray>
}