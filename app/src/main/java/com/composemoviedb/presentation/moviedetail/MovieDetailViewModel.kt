package com.composemoviedb.presentation.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.composemoviedb.core.utils.Constants.MOVIE_ID_ARGS
import com.composemoviedb.core.utils.Resource
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
internal class MovieDetailViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val movieId = savedStateHandle.get<Int>(MOVIE_ID_ARGS)

    private val _state = MutableStateFlow(MovieDetailState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        onEvent(MovieDetailEvent.OnMovieDetailLoaded)
        onEvent(MovieDetailEvent.OnReviewLoaded)
    }

    fun onEvent(event: MovieDetailEvent) {
        when (event) {
            MovieDetailEvent.OnMovieDetailLoaded -> {
                getMovieDetail(movieId = movieId ?: 0)
            }

            MovieDetailEvent.OnReviewLoaded -> {
                getReviewList(movieId = movieId ?: 0)
            }

            is MovieDetailEvent.OnTabClicked -> {
                onTabClicked(event.index)
            }

            MovieDetailEvent.OnBackButtonClicked -> {
                onBackButtonClicked()
            }
        }
    }

    private fun onBackButtonClicked() {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.PopBackStack)
        }
    }

    private fun onTabClicked(index: Int) {
        _state.update { it.copy(tabIndex = index) }
    }

    private fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            movieUseCase.getMovieById(movieId).collect { resource ->
                when (resource) {
                    Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                movieDetailData = null,
                                isError = null
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                movieDetailData = resource.data,
                                isError = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                movieDetailData = null,
                                isError = resource.errorMessage,
                            )
                        }
                        _uiEvent.send(UiEvent.ShowErrorMessage(resource.errorMessage))
                    }
                }
            }
        }
    }

    private fun getReviewList(movieId: Int) {
        _state.update {
            it.copy(
                reviewList = movieUseCase.getMovieReview(movieId = movieId)
                    .distinctUntilChanged()
                    .cachedIn(viewModelScope),
            )
        }
    }
}