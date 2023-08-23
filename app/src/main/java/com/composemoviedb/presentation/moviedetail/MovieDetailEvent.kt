package com.composemoviedb.presentation.moviedetail

sealed interface MovieDetailEvent {
    data object OnMovieDetailLoaded : MovieDetailEvent
    data object OnReviewLoaded : MovieDetailEvent
    data class OnTabClicked(val index: Int) : MovieDetailEvent
    data object OnBackButtonClicked : MovieDetailEvent
}