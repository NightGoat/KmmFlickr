package ru.nightgoat.kmmflickr.di

import io.ktor.client.*
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.nightgoat.kmmflickr.data.FlickrImagesRemoteDataSource
import ru.nightgoat.kmmflickr.data.IImagesRepository
import ru.nightgoat.kmmflickr.data.ImagesRemoteDataSource
import ru.nightgoat.kmmflickr.data.ImagesRepository
import ru.nightgoat.kmmflickr.domain.DownloadImageUseCase
import ru.nightgoat.kmmflickr.domain.GetImagesUseCase
import ru.nightgoat.kmmflickr.domain.IDownloadImageUseCase
import ru.nightgoat.kmmflickr.domain.IGetImagesUseCase
import ru.nightgoat.kmmflickr.providers.http.HttpClientProvider

private const val jsonHttpClient = "json"

val imageModules = module {
    single {
        GetImagesUseCase(get()) as IGetImagesUseCase
    }
    single {
        DownloadImageUseCase(get()) as IDownloadImageUseCase
    }
    single {
        ImagesRepository(get()) as IImagesRepository
    }
    single {
        FlickrImagesRemoteDataSource(get(named(jsonHttpClient))) as ImagesRemoteDataSource
    }
    single(named(jsonHttpClient)) {
        HttpClientProvider.provideJsonClient() as HttpClient
    }
}