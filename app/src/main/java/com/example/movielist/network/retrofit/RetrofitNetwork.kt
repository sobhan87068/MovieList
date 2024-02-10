package com.example.movielist.network.retrofit

import com.example.movielist.BuildConfig
import com.example.movielist.network.NetworkDataSource
import com.example.movielist.network.model.UpcomingPage
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitNetworkApi {

    @GET("3/movie/upcoming")
    suspend fun getMoviesList(
        @Query("page") page: Int
    ): UpcomingPage
}

private const val BASE_URL = BuildConfig.API_URL

@Singleton
class RetrofitNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
) : NetworkDataSource {

    private val moviesApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(RetrofitNetworkApi::class.java)

    override suspend fun getMoviesList(page: Int): UpcomingPage {
        return moviesApi.getMoviesList(page)
    }
}