package com.example.movielist.base

sealed class ViewState {
    data object Idle : ViewState()

    data object Loading : ViewState()

    data class Error(val code: Int, val message: String? = null) : ViewState()

    data class Success<T : Any>(val data: T) : ViewState()
}