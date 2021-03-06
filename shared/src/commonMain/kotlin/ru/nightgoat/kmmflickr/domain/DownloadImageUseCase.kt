package ru.nightgoat.kmmflickr.domain

import com.soywiz.korim.bitmap.Bitmap
import ru.nightgoat.kmmflickr.core.base.IUseCase
import ru.nightgoat.kmmflickr.data.IImagesRepository
import ru.nightgoat.kmmflickr.models.ui.PhotoUi

class DownloadImageUseCase(
    private val repo: IImagesRepository
) : IDownloadImageUseCase {
    override suspend fun invoke(param: PhotoUi): Result<Bitmap> {
        return repo.downloadPhotoBitmap(param)
    }
}

/** Use case to download photo's bytes */
interface IDownloadImageUseCase : IUseCase.InOut<PhotoUi, Result<Bitmap>>