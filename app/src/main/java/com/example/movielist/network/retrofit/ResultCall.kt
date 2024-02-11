package com.example.movielist.network.retrofit

import okhttp3.Request
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ResultCall<T>(private val delegate: Call<T>) : Call<Result<T>> {
    override fun clone(): Call<Result<T>> {
        return ResultCall(delegate.clone())
    }

    override fun execute(): Response<Result<T>> {
        return Response.success(Result.success(delegate.execute().body()!!))
    }

    override fun isExecuted() = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled() = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

    override fun enqueue(callback: Callback<Result<T>>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    callback.onResponse(
                        this@ResultCall,
                        Response.success(
                            response.code(),
                            Result.success(response.body()!!)
                        )
                    )
                } else {
                    callback.onResponse(
                        this@ResultCall,
                        Response.success(
                            Result.failure(
                                HttpException(response)
                            )
                        )
                    )
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val errorMessage = when (t) {
                    is IOException -> "No internet connection"
                    is HttpException -> "Something went wrong!"
                    else -> t.localizedMessage
                }
                callback.onResponse(
                    this@ResultCall,
                    Response.success(Result.failure(RuntimeException(errorMessage, t)))
                )
            }

        })
    }
}