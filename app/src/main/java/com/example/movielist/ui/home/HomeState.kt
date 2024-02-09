package com.example.movielist.ui.home

import com.example.movielist.base.ViewState
import com.example.movielist.data.model.Movie

sealed class HomeState : ViewState {
    data object Idle : HomeState()

    data object Loading : HomeState()

    data class Error(val code: Int, val message: String? = null) : HomeState()

    sealed class NewPage(val data: List<Movie>) : HomeState() {
        data class PaginationLoading(
            private val currentMovies: List<Movie>
        ) : NewPage(currentMovies)

        data class Success(
            private val currentMovies: List<Movie>
        ) : NewPage(currentMovies)

        data class PaginationError(
            private val currentMovies: List<Movie>,
            val code: Int,
            val message: String? = null
        ) : NewPage(currentMovies)
    }
}