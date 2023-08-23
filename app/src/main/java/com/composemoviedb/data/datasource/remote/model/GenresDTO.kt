package com.composemoviedb.data.datasource.remote.model


import com.google.gson.annotations.SerializedName

data class GenresDTO(
    @SerializedName("genres") val genres: List<GenreDTO?> = listOf(),
)