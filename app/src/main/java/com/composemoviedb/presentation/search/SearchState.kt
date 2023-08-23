package com.composemoviedb.presentation.search

import com.composemoviedb.domain.entities.MovieListResult

data class SearchState(
    val isLoading: Boolean = false,
    val movieResult: List<MovieListResult>? = emptyList(),
    val isError: String? = null,
    val searchQuery: String = "",
)
