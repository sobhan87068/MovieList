package com.example.movielist.domain

import com.example.movielist.data.model.Movie
import com.example.movielist.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpcomingUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(page: Int): Flow<List<Movie>> {
        return repository.getMoviesList(page)
    }
}