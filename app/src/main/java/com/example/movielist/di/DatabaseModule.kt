package com.example.movielist.di

import android.content.Context
import androidx.room.Room
import com.example.movielist.database.TmdbCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesTmdbCache(
        @ApplicationContext context: Context,
    ): TmdbCache = Room.databaseBuilder(
        context,
        TmdbCache::class.java,
        "nia-database",
    ).build()
}