package com.example.movielist.data.repository

import com.example.movielist.data.model.Movie
import com.example.movielist.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dataSource: NetworkDataSource
) : Repository {
    override suspend fun getMoviesList(page: Int): Flow<List<Movie>> {
        return flow {
            val pageData = dataSource.getMoviesList(page)
            emit(pageData.movies.map { remoteMovie ->
                Movie(remoteMovie.id, remoteMovie.title, remoteMovie.posterPath)
            })
        }
    }
}