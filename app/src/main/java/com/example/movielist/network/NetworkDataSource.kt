package com.example.movielist.network

import com.example.movielist.network.model.UpcomingPage

interface NetworkDataSource {
    suspend fun getMoviesList(page: Int): UpcomingPage
}