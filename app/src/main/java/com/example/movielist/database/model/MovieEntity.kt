package com.example.movielist.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movielist.data.model.Movie
import com.example.movielist.network.model.RemoteMovie

@Entity(
    tableName = "movies"
)
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val posterPath: String,
    val createdAt: Long = System.currentTimeMillis()
)

fun MovieEntity.asExternalModel() = Movie(
    id = id,
    title = title,
    thumbnail = posterPath
)

fun RemoteMovie.toDbEntity() = MovieEntity(
    id = id,
    title = title ?: "",
    posterPath = posterPath ?: ""
)