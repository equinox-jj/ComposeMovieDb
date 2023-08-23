package com.composemoviedb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.composemoviedb.core.utils.Resource
import com.composemoviedb.data.datasource.remote.ApiService
import com.composemoviedb.data.datasource.remote.pagingsource.MoviePagingSource
import com.composemoviedb.data.datasource.remote.pagingsource.ReviewPagingSource
import com.composemoviedb.data.mapper.toDomain
import com.composemoviedb.domain.entities.GenreResult
import com.composemoviedb.domain.entities.MovieDetailResponse
import com.composemoviedb.domain.entities.MovieListResult
import com.composemoviedb.domain.entities.ReviewResult
import com.composemoviedb.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: ApiService) : Repository {
    override fun getMovieList(genre: String): Flow<PagingData<MovieListResult>> = Pager(
        config = PagingConfig(
            enablePlaceholders = false,
            pageSize = 10,
            initialLoadSize = 15,
        ),
        pagingSourceFactory = { MoviePagingSource(apiService = apiService, genre = genre) }
    ).flow

    override fun getMovieReview(movieId: Int): Flow<PagingData<ReviewResult>> = Pager(
        config = PagingConfig(
            enablePlaceholders = false,
            pageSize = 10,
            initialLoadSize = 15,
        ),
        pagingSourceFactory = { ReviewPagingSource(apiService = apiService, movieId = movieId) }
    ).flow

    override fun getGenreList(): Flow<Resource<List<GenreResult>>> = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.getGenreList().genres
            val genreResult = response.mapNotNull { it?.toDomain() }

            emit(Resource.Success(genreResult))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message()))
        } catch (e: IOException) {
            emit(Resource.Error(e.message))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }

    override fun getMovieById(movieId: Int): Flow<Resource<MovieDetailResponse>> = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.getMovieById(movieId = movieId)
            val movieDetailResult = response.toDomain()

            emit(Resource.Success(movieDetailResult))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message()))
        } catch (e: IOException) {
            emit(Resource.Error(e.message))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }

    override fun searchMovie(query: String): Flow<Resource<List<MovieListResult>>> = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.searchMovie(query = query)
            val searchResult = response.results.mapNotNull { it?.toDomain() }

            emit(Resource.Success(searchResult))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message()))
        } catch (e: IOException) {
            emit(Resource.Error(e.message))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }
    }
}