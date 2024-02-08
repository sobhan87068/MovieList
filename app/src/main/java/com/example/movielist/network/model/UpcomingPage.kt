package com.example.movielist.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpcomingPage(
    @SerialName("page") val page: Int,
    @SerialName("dates") val dates: DateRange,
    @SerialName("results") val movies: List<RemoteMovie>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

@Serializable
data class DateRange(
    @SerialName("maximum") val maximum: String,
    @SerialName("minimum") val minimum: String
)