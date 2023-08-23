package com.composemoviedb.presentation.discover

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.composemoviedb.core.utils.UiEvent
import com.composemoviedb.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val genreList = savedStateHandle.get<String>("genreList")

    private val _state = MutableStateFlow(DiscoverState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        onEvent(DiscoverEvent.OnDiscoverMovieLoaded)
    }

    fun onEvent(event: DiscoverEvent) {
        when (event) {
            DiscoverEvent.OnDiscoverMovieLoaded -> {
                getMovieList(genre = genreList.toString())
            }

            DiscoverEvent.OnBackButtonClicked -> {
                navigateBack()
            }

            is DiscoverEvent.OnMovieCardClicked -> {
                navigateToDetailScreen(event.movieId)
            }
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.PopBackStack)
        }
    }

    private fun navigateToDetailScreen(movieId: Int) {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.NavigateToDetailScreen(movieId))
        }
    }

    private fun getMovieList(genre: String = "") {
        _state.update {
            it.copy(
                movieList = movieUseCase.getMovieList(genre = genre)
                    .distinctUntilChanged()
                    .cachedIn(viewModelScope),
                genreList = genreList?.split(", ")?.toList().orEmpty(),
            )
        }
    }

}