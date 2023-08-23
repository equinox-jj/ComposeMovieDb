package com.composemoviedb.presentation.genrelist

import androidx.compose.runtime.Immutable
import com.composemoviedb.domain.entities.GenreResult

@Immutable
data class GenreListState(
    val isLoading: Boolean = false,
    val genreList: List<GenreResult>? = emptyList(),
    val isError: String? = null,
    val selectedChip: MutableList<String> = mutableListOf(),
)