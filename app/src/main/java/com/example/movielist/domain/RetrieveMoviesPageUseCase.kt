package com.example.movielist.domain

import com.example.movielist.data.repository.Repository
import com.example.movielist.data.result.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveMoviesPageUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(page: Int): Flow<ApiResult> {
        return repository.syncMovies(page)
    }
}