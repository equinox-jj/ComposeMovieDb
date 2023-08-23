package com.composemoviedb.presentation.discover

sealed interface DiscoverEvent {
    data object OnDiscoverMovieLoaded : DiscoverEvent
    data class OnMovieCardClicked(val movieId: Int) : DiscoverEvent
    data object OnBackButtonClicked : DiscoverEvent
}