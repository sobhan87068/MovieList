package com.example.movielist.data.repository

import com.example.movielist.data.model.Movie
import com.example.movielist.data.result.ApiResult
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getMoviesList(): Flow<List<Movie>>

    suspend fun syncMovies(page: Int): Flow<ApiResult>
}