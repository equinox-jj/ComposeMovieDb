package com.composemoviedb.data.datasource.remote.model


import com.google.gson.annotations.SerializedName

data class ProductionCountryDTO(
    @SerializedName("iso_3166_1") val iso31661: String? = "",
    @SerializedName("name") val name: String? = "",
)