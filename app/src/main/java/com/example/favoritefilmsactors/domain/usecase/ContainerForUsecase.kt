package com.example.favoritefilmsactors.domain.usecase

import com.example.favoritefilmsactors.domain.MovieRepository
import javax.inject.Inject

class ContainerForUsecase @Inject constructor(private val repository: MovieRepository) {


}