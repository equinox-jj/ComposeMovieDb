package com.composemoviedb.core.utils

sealed interface UiEvent {
    data object NavigateToDiscoverScreen : UiEvent
    data object NavigateToSearchScreen : UiEvent
    data class NavigateToDetailScreen(val movieId: Int) : UiEvent
    data class ShowSuccessMessage(val message: String?) : UiEvent
    data class ShowErrorMessage(val message: String?) : UiEvent
    data class ShowGenreErrorMessage(val message: String?) : UiEvent
    data object PopBackStack : UiEvent
}