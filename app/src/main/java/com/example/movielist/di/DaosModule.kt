package com.example.movielist.di

import com.example.movielist.database.TmdbCache
import com.example.movielist.database.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun provideMovieDao(database: TmdbCache): MovieDao = database.movieDao()
}