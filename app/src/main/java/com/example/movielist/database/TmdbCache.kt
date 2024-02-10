package com.example.movielist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movielist.database.dao.MovieDao
import com.example.movielist.database.model.MovieEntity

@Database(
    entities = [
        MovieEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class TmdbCache : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}