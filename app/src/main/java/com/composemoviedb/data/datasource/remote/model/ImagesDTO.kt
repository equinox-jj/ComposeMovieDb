package com.composemoviedb.data.datasource.remote.model


import com.google.gson.annotations.SerializedName

data class ImagesDTO(
    @SerializedName("backdrops") val backdrops: List<Any?> = listOf(),
    @SerializedName("logos") val logos: List<Any?> = listOf(),
    @SerializedName("posters") val posters: List<Any?> = listOf(),
)