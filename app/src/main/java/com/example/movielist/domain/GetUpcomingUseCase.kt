package com.example.movielist.domain

import com.example.movielist.data.model.Movie
import com.example.movielist.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpcomingUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(): Flow<List<Movie>> {
        return repository.getMoviesList()
    }
}