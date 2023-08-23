package com.composemoviedb.data.datasource.remote.model


import com.google.gson.annotations.SerializedName

data class BelongsToCollectionDTO(
    @SerializedName("backdrop_path") val backdropPath: String? = "",
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("name") val name: String? = "",
    @SerializedName("poster_path") val posterPath: String? = "",
)