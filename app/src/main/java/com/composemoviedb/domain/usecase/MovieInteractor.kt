package com.composemoviedb.domain.usecase

import androidx.paging.PagingData
import com.composemoviedb.core.utils.Resource
import com.composemoviedb.domain.entities.GenreResult
import com.composemoviedb.domain.entities.MovieDetailResponse
import com.composemoviedb.domain.entities.MovieListResult
import com.composemoviedb.domain.entities.ReviewResult
import com.composemoviedb.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val repository: Repository) : MovieUseCase {
    override fun getMovieList(genre: String): Flow<PagingData<MovieListResult>> =
        repository.getMovieList(genre = genre)

    override fun getMovieReview(movieId: Int): Flow<PagingData<ReviewResult>> =
        repository.getMovieReview(movieId = movieId)

    override fun getGenreList(): Flow<Resource<List<GenreResult>>> = repository.getGenreList()

    override fun getMovieById(movieId: Int): Flow<Resource<MovieDetailResponse>> =
        repository.getMovieById(movieId = movieId)

    override fun searchMovie(query: String): Flow<Resource<List<MovieListResult>>> =
        repository.searchMovie(query = query)

}