package com.example.movielist.di

import com.example.movielist.data.repository.Repository
import com.example.movielist.data.repository.RepositoryImpl
import com.example.movielist.network.NetworkDataSource
import com.example.movielist.network.retrofit.RetrofitNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindRepository(
        repository: RepositoryImpl
    ): Repository

    @Binds
    fun bindNetworkDataSource(
        retrofitNetwork: RetrofitNetwork
    ): NetworkDataSource
}