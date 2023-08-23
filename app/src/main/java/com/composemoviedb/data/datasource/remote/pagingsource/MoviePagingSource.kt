package com.composemoviedb.data.datasource.remote.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.composemoviedb.data.datasource.remote.ApiService
import com.composemoviedb.data.mapper.toDomain
import com.composemoviedb.domain.entities.MovieListResult
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val apiService: ApiService,
    private val genre: String = "",
) : PagingSource<Int, MovieListResult>() {
    override fun getRefreshKey(state: PagingState<Int, MovieListResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieListResult> {
        return try {
            val position = params.key ?: 1

            val response = apiService.getMovieList(page = position, withGenres = genre)
            val movieResult = response.results.mapNotNull { it?.toDomain() }

            val nextKey = if (movieResult.isEmpty()) null else position.plus(1)

            LoadResult.Page(
                data = movieResult,
                prevKey = null,
                nextKey = nextKey,
            )
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}