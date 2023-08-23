package com.composemoviedb.presentation.genrelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composemoviedb.core.utils.Resource
import com.composemoviedb.core.utils.UiEvent
import com.composemoviedb.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class GenreListViewModel @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow(GenreListState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        onEvent(GenreListEvent.OnGenreListLoaded)
    }

    fun onEvent(event: GenreListEvent) {
        when (event) {
            is GenreListEvent.GenreChipClicked -> {
                _state.update {
                    it.copy(selectedChip = mutableListOf(event.selectedChip.toString()))
                }
            }

            GenreListEvent.OnGenreListLoaded -> {
                getGenreList()
            }

            GenreListEvent.OnSubmitClicked -> {
                navigateToDiscoverScreen()
            }

            is GenreListEvent.ShowErrorMessage -> {
                showErrorMessage(event.message)
            }

            GenreListEvent.OnSearchClicked -> {
                navigateToSearchScreen()
            }
        }
    }

    private fun showErrorMessage(message: String?) {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.ShowGenreErrorMessage(message))
        }
    }

    private fun navigateToSearchScreen() {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.NavigateToSearchScreen)
        }
    }

    private fun navigateToDiscoverScreen() {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.NavigateToDiscoverScreen)
        }
    }

    private fun getGenreList() {
        viewModelScope.launch {
            movieUseCase.getGenreList().collect { resource ->
                when (resource) {
                    Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                genreList = emptyList(),
                                isError = null,
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                genreList = resource.data,
                                isError = null,
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                genreList = emptyList(),
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