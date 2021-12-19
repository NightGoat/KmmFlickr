package ru.nightgoat.kmmflickr.domain

import ru.nightgoat.kmmflickr.core.base.IUseCase
import ru.nightgoat.kmmflickr.data.IImagesRepository
import ru.nightgoat.kmmflickr.models.ui.PhotoUi

class DownloadImageUseCase(
    private val repo: IImagesRepository
) : IDownloadImageUseCase {
    override suspend fun invoke(param: PhotoUi): Result<ByteArray> {
        return repo.downloadPhoto(param)
    }
}

interface IDownloadImageUseCase : IUseCase.InOut<PhotoUi, Result<ByteArray>>