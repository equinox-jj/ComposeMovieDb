package com.composemoviedb.presentation.search

sealed interface SearchEvent {
    data class OnSearchQueryChanged(val query: String) : SearchEvent
    data object OnBackButtonClicked : SearchEvent
    data class OnSearchMovieCardClicked(val movieId: Int) : SearchEvent
}