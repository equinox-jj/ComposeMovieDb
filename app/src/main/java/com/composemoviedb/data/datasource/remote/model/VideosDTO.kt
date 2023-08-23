package com.composemoviedb.data.datasource.remote.model


import com.google.gson.annotations.SerializedName

data class VideosDTO(
    @SerializedName("results") val results: List<MovieDetailResultDTO?> = listOf(),
)