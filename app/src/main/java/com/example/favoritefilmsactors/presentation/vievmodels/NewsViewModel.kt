package com.example.favoritefilmsactors.presentation.vievmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.favoritefilmsactors.domain.usecase.nevs.GetTopNevsUseCase
import com.example.favoritefilmsactors.utils.CurrentResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val application: Application,
    private val getTopNevsUseCase: GetTopNevsUseCase
) : ViewModel() {

    val nevsFlov = flow {
        emit(CurrentResult.Loading( true))
        val result = getTopNevsUseCase.execute(1, "Ukraine")
        result.exception?.let {
            emit(CurrentResult.Error(it.message.toString()))
        }
        emit(CurrentResult.Success(result.data))
    }


}