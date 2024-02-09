package com.example.movielist.ui.home

import com.example.movielist.base.ViewState
import com.example.movielist.data.model.Movie

sealed class HomeState : ViewState {
    data object Idle : HomeState()

    data object Loading : HomeState()

    data object PaginationLoading : HomeState()

    data class PaginationError(val code: Int, val message: String? = null) : HomeState()

    data class Error(val code: Int, val message: String? = null) : HomeState()

    data class Success(val data: List<Movie>) : HomeState()
}