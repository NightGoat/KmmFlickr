package ru.nightgoat.kmmflickr.domain

import ru.nightgoat.kmmflickr.core.base.IUseCase
import ru.nightgoat.kmmflickr.data.IImagesRepository
import ru.nightgoat.kmmflickr.models.ui.PhotoUi

class GetImagesUseCase(
    private val repository: IImagesRepository
) : IGetImagesUseCase {
    override suspend fun invoke(param: String): Result<List<PhotoUi>> {
        return repository.loadImages(param)
    }
}

/** use case calls api and returns ui models */
interface IGetImagesUseCase : IUseCase.InOut<String, Result<List<PhotoUi>>>