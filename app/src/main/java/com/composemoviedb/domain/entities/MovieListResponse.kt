package com.composemoviedb.domain.entities

data class MovieListResponse(
    val page: Int? = 0,
    val results: List<MovieListResult?> = listOf(),
)