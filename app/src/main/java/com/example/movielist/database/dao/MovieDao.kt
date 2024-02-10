package com.example.movielist.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.movielist.database.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query(
        value = "SELECT * FROM movies ORDER BY createdAt Asc"
    )
    fun getMovieEntities(): Flow<List<MovieEntity>>

    @Upsert
    fun upsertMovies(entities: List<MovieEntity>)

    @Query(
        value = "DELETE FROM movies"
    )
    fun clearMovies()

    @Transaction
    fun updateMovies(entities: List<MovieEntity>, shouldReset: Boolean) {
        if (shouldReset)
            clearMovies()
        upsertMovies(entities)
    }
}