package com.composemoviedb.data.datasource.remote.model


import com.google.gson.annotations.SerializedName

data class MovieResponseDTO(
    @SerializedName("page") val page: Int? = 0,
    @SerializedName("results") val results: List<MovieResultDTO?> = listOf(),
    @SerializedName("total_pages") val totalPages: Int? = 0,
    @SerializedName("total_results") val totalResults: Int? = 0,
)