package com.composemoviedb.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composemoviedb.core.utils.Resource
import com.composemoviedb.core.utils.UiEvent
import com.composemoviedb.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var searchJob: Job? = null

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnSearchQueryChanged -> {
                _state.update {
                    it.copy(
                        searchQuery = event.query
                    )
                }
                searchMovie(event.query)
            }

            SearchEvent.OnBackButtonClicked -> {
                onNavigateBack()
            }

            is SearchEvent.OnSearchMovieCardClicked -> {
                onSearchMovieCardClicked(event.movieId)
            }
        }
    }

    private fun onSearchMovieCardClicked(movieId: Int) {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.NavigateToDetailScreen(movieId))
        }
    }

    private fun onNavigateBack() {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.PopBackStack)
        }
    }

    private fun searchMovie(query: String = "") {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(1500)
            movieUseCase.searchMovie(query = query).collect { resource ->
                when (resource) {
                    Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                movieResult = emptyList(),
                                isError = null,
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                movieResult = resource.data,
                                isError = null,
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                movieResult = emptyList(),
                                isError = resource.errorMessage,
                            )
                        }
                        _uiEvent.send(UiEvent.ShowErrorMessage(resource.errorMessage))
                    }
                }
            }
        }
    }
}