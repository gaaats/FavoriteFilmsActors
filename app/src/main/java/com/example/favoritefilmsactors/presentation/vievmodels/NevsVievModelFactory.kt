package com.example.favoritefilmsactors.presentation.vievmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.favoritefilmsactors.domain.usecase.nevs.GetTopNevsUseCase
import javax.inject.Inject

class NevsVievModelFactory @Inject constructor(
    private val application: Application,
    private val getTopNevsUseCase: GetTopNevsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(application, getTopNevsUseCase) as T
        } else{
            throw IllegalArgumentException("there is no such ViewModel")
        }
    }
}