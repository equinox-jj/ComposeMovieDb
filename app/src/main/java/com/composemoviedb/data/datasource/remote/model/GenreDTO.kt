package com.composemoviedb.data.datasource.remote.model


import com.google.gson.annotations.SerializedName

data class GenreDTO(
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("name") val name: String? = "",
)