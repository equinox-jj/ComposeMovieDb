package com.composemoviedb.presentation.moviedetail

import androidx.paging.PagingData
import com.composemoviedb.domain.entities.MovieDetailResponse
import com.composemoviedb.domain.entities.ReviewResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MovieDetailState(
    val isLoading: Boolean = false,
    val movieDetailData: MovieDetailResponse? = null,
    val reviewList: Flow<PagingData<ReviewResult>>? = emptyFlow(),
    val isError: String? = null,
    val tabIndex: Int = 0,
)
