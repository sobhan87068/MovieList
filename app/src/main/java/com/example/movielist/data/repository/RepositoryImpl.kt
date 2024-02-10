package com.example.movielist.data.repository

import com.example.movielist.data.model.Movie
import com.example.movielist.data.result.ApiResult
import com.example.movielist.database.dao.MovieDao
import com.example.movielist.database.model.MovieEntity
import com.example.movielist.database.model.asExternalModel
import com.example.movielist.database.model.toDbEntity
import com.example.movielist.network.NetworkDataSource
import com.example.movielist.network.model.RemoteMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val dataSource: NetworkDataSource,
) : Repository {
    override fun getMoviesList(): Flow<List<Movie>> {
        return movieDao.getMovieEntities().map {
            it.map(MovieEntity::asExternalModel)
        }
    }

    override suspend fun syncMovies(page: Int): Flow<ApiResult> {
        return flow {
            emit(ApiResult.ApiLoading)
            val networkMovies = dataSource.getMoviesList(page)

            withContext(Dispatchers.IO) {
                movieDao.updateMovies(
                    networkMovies.movies.map(RemoteMovie::toDbEntity),
                    page == 1
                )
            }
            emit(ApiResult.ApiSuccess(networkMovies.totalPages))
        }
    }
}