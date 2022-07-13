package com.example.favoritefilmsactors.presentation.vievmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.favoritefilmsactors.data.room.entity.images.ImagesItem
import com.example.favoritefilmsactors.domain.usecase.GetMovImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PagerMovieVievModel @Inject constructor(
    private val application: Application,
    private val getImages: GetMovImagesUseCase,
) : ViewModel() {

    private var _listImages = MutableLiveData<List<ImagesItem>>()
    val listImages: LiveData<List<ImagesItem>>
        get() = _listImages

    suspend fun loadImagesList(imageId: Int) {
        withContext(Dispatchers.Main) {
            _listImages.value = getImages(imageId).data
        }
    }
}