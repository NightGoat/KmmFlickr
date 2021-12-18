package ru.nightgoat.kmmflickr.di

import org.koin.dsl.module
import ru.nightgoat.kmmflickr.data.FlickrImagesRemoteDataSource
import ru.nightgoat.kmmflickr.data.IImagesRepository
import ru.nightgoat.kmmflickr.data.ImagesRemoteDataSource
import ru.nightgoat.kmmflickr.data.ImagesRepository
import ru.nightgoat.kmmflickr.domain.GetImagesUseCase
import ru.nightgoat.kmmflickr.domain.IGetImagesUseCase

val imageModules = module {
    single {
        GetImagesUseCase(get()) as IGetImagesUseCase
    }
    single {
        ImagesRepository(get()) as IImagesRepository
    }
    single {
        FlickrImagesRemoteDataSource() as ImagesRemoteDataSource
    }

}