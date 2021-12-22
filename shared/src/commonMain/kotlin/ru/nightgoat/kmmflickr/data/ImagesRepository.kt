package ru.nightgoat.kmmflickr.data

import com.soywiz.korim.bitmap.Bitmap
import ru.nightgoat.kmmflickr.core.base.IRemoteRepository
import ru.nightgoat.kmmflickr.core.base.mapConvertibles
import ru.nightgoat.kmmflickr.models.ui.PhotoUi


class ImagesRepository(
    override val remoteDataSource: ImagesRemoteDataSource
) : IImagesRepository {

    override suspend fun loadImages(tag: String): Result<List<PhotoUi>> {
        return kotlin.runCatching {
            val raw = remoteDataSource.getPhotos(tag)
            raw.photos?.photos.mapConvertibles()
        }
    }

    override suspend fun downloadPhotoByteArray(photoUi: PhotoUi): Result<ByteArray> {
        return kotlin.runCatching {
            remoteDataSource.downloadPhotoByteArray(photoUi)
        }
    }

    override suspend fun downloadPhotoBitmap(photoUi: PhotoUi): Result<Bitmap> {
        return kotlin.runCatching {
            remoteDataSource.downloadPhotoBitmap(photoUi)
        }
    }

}

/**
 * Repository to work with remote image resources.
 * */
interface IImagesRepository : IRemoteRepository<ImagesRemoteDataSource> {
    /** calls api and parses response
     * @param tag user search input for image */
    suspend fun loadImages(tag: String): Result<List<PhotoUi>>

    /** downloads image with link to byte array */
    suspend fun downloadPhotoByteArray(photoUi: PhotoUi): Result<ByteArray>
    suspend fun downloadPhotoBitmap(photoUi: PhotoUi): Result<Bitmap>
}