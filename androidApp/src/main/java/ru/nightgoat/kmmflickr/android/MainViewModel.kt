package ru.nightgoat.kmmflickr.android

import androidx.lifecycle.ViewModel
import com.soywiz.korim.format.toAndroidBitmap
import kotlinx.coroutines.launch
import ru.nightgoat.kmmflickr.models.ui.PhotoUi
import ru.nightgoat.kmmflickr.presentation.BaseViewModel
import ru.nightgoat.kmmflickr.presentation.IBaseViewModel
import ru.nightgoat.kmmflickr.presentation.MainSideEffect


class MainViewModel : ViewModel(), IBaseViewModel by BaseViewModel() {

    fun downloadImage(photoUi: PhotoUi) {
        launch {
            clearSideEffect()
            downloadImageUseCase(photoUi).onSuccess { korimBitmap ->
                val bitmap = korimBitmap.toAndroidBitmap()
                MainSideEffect.SaveImageToGallery(
                    bitmap = bitmap,
                    photoUi = photoUi
                ).reduce()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        clearJobsAndSubscriptions()
    }
}