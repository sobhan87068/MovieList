package com.example.movielist.data.repository

import com.example.movielist.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getMoviesList(page: Int): Flow<List<Movie>>
}