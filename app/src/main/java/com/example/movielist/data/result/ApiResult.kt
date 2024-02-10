package com.example.movielist.data.result

sealed class ApiResult {

    data object ApiLoading : ApiResult()
    data class ApiSuccess(val pageCount: Int) : ApiResult()

    data class ApiError(val code: Int, val message: String? = "") : ApiResult()
}