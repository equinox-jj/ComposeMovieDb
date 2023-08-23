package com.composemoviedb.data.datasource.remote.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.composemoviedb.data.datasource.remote.ApiService
import com.composemoviedb.data.mapper.toDomain
import com.composemoviedb.domain.entities.ReviewResult
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ReviewPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val movieId: Int,
) : PagingSource<Int, ReviewResult>() {
    override fun getRefreshKey(state: PagingState<Int, ReviewResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewResult> {
        return try {
            val position = params.key ?: 1

            val response = apiService.getReviews(page = position, movieId = movieId)
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