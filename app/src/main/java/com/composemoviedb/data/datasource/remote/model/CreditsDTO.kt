package com.composemoviedb.data.datasource.remote.model


import com.google.gson.annotations.SerializedName

data class CreditsDTO(
    @SerializedName("cast") val cast: List<CastDTO?> = listOf(),
    @SerializedName("crew") val crew: List<CrewDTO?> = listOf(),
)