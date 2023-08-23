package com.composemoviedb.data.datasource.remote

import com.composemoviedb.core.utils.ApiKey.API_KEY
import com.composemoviedb.core.utils.Constants.DISCOVER_MOVIE_EP
import com.composemoviedb.data.datasource.remote.model.GenresDTO
import com.composemoviedb.data.datasource.remote.model.MovieDetailResponseDTO
import com.composemoviedb.data.datasource.remote.model.MovieResponseDTO
import com.composemoviedb.data.datasource.remote.model.ReviewResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(DISCOVER_MOVIE_EP)
    suspend fun getMovieList(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("with_genres") withGenres: String = "",
    ): MovieResponseDTO

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("append_to_response") appendToResponse: String = "videos,credits,images",
    ): MovieDetailResponseDTO

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String = "",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): MovieResponseDTO

    @GET("genre/movie/list")
    suspend fun getGenreList(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en",
    ): GenresDTO

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): ReviewResponseDTO
}