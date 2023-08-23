package com.composemoviedb.domain.usecase

import androidx.paging.PagingData
import com.composemoviedb.core.utils.Resource
import com.composemoviedb.domain.entities.GenreResult
import com.composemoviedb.domain.entities.MovieDetailResponse
import com.composemoviedb.domain.entities.MovieListResult
import com.composemoviedb.domain.entities.ReviewResult
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMovieList(genre: String = ""): Flow<PagingData<MovieListResult>>
    fun getMovieReview(movieId: Int): Flow<PagingData<ReviewResult>>
    fun getGenreList(): Flow<Resource<List<GenreResult>>>
    fun getMovieById(movieId: Int): Flow<Resource<MovieDetailResponse>>
    fun searchMovie(query: String): Flow<Resource<List<MovieListResult>>>
}