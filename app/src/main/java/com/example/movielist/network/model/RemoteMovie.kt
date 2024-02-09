package com.example.movielist.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteMovie(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String?,
    @SerialName("overview") val overview: String?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("genre_ids") val genreIds: IntArray?,
    @SerialName("original_language") val originalLanguage: String?,
    @SerialName("original_title") val originalTitle: String?,
    @SerialName("popularity") val popularity: Float?,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String?,
    @SerialName("video") val video: Boolean?,
    @SerialName("vote_average") val voteAverage: Float?,
    @SerialName("vote_count") val voteCount: Int?,
    @SerialName("adult") val adult: Boolean?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RemoteMovie

        if (id != other.id) return false
        if (title != other.title) return false
        if (overview != other.overview) return false
        if (backdropPath != other.backdropPath) return false
        if (!genreIds.contentEquals(other.genreIds)) return false
        if (originalLanguage != other.originalLanguage) return false
        if (originalTitle != other.originalTitle) return false
        if (popularity != other.popularity) return false
        if (posterPath != other.posterPath) return false
        if (releaseDate != other.releaseDate) return false
        if (video != other.video) return false
        if (voteAverage != other.voteAverage) return false
        if (voteCount != other.voteCount) return false
        return adult == other.adult
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + overview.hashCode()
        result = 31 * result + backdropPath.hashCode()
        result = 31 * result + genreIds.contentHashCode()
        result = 31 * result + originalLanguage.hashCode()
        result = 31 * result + originalTitle.hashCode()
        result = 31 * result + popularity.hashCode()
        result = 31 * result + posterPath.hashCode()
        result = 31 * result + releaseDate.hashCode()
        result = 31 * result + video.hashCode()
        result = 31 * result + voteAverage.hashCode()
        result = 31 * result + voteCount.hashCode()
        result = 31 * result + adult.hashCode()
        return result
    }
}