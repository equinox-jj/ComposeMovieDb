package com.composemoviedb.domain.entities

data class MovieDetailResponse(
    val adult: Boolean? = false,
    val backdropPath: String? = "",
    val budget: Int? = 0,
    val credits: CreditsResponse? = CreditsResponse(),
    val genres: List<GenreResult?> = listOf(),
    val homepage: String? = "",
    val id: Int? = 0,
    val images: ImagesResult? = ImagesResult(),
    val imdbId: String? = "",
    val originalLanguage: String? = "",
    val originalTitle: String? = "",
    val overview: String? = "",
    val popularity: Double? = 0.0,
    val posterPath: String? = "",
    val releaseDate: String? = "",
    val revenue: Int? = 0,
    val runtime: Int? = 0,
    val status: String? = "",
    val tagline: String? = "",
    val title: String? = "",
    val video: Boolean? = false,
    val videos: VideoResponse? = VideoResponse(),
    val voteAverage: Double? = 0.0,
    val voteCount: Int? = 0,
)