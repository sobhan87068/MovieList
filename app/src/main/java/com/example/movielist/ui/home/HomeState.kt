package com.example.movielist.ui.home

import com.example.movielist.base.ViewState
import com.example.movielist.data.model.Movie

sealed class HomeState(val data: List<Movie>) : ViewState {

    data object Idle : HomeState(listOf())
    data class Loading(
        private val currentMovies: List<Movie>
    ) : HomeState(currentMovies)

    data class Success(
        private val currentMovies: List<Movie>
    ) : HomeState(currentMovies)

    data class Error(
        private val currentMovies: List<Movie>,
        val message: String? = null
    ) : HomeState(currentMovies)
}