package com.composemoviedb.presentation.genrelist

sealed interface GenreListEvent {
    data object OnGenreListLoaded : GenreListEvent
    data class GenreChipClicked(
        val selectedChip: String? = null,
        val isChipSelected: Boolean = false,
    ) : GenreListEvent

    data object OnSubmitClicked : GenreListEvent
    data object OnSearchClicked : GenreListEvent
    data class ShowErrorMessage(val message: String?) : GenreListEvent
}