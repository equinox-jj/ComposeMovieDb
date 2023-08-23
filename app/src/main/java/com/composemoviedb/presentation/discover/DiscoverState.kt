package com.composemoviedb.presentation.discover

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.composemoviedb.domain.entities.MovieListResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class DiscoverState(
    val isLoading: Boolean = false,
    val movieList: Flow<PagingData<MovieListResult>> = emptyFlow(),
    val genreList: List<String> = listOf(),
    val isError: String? = null,
)
