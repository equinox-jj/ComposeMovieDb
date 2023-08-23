package com.composemoviedb.data.datasource.remote.model


import com.google.gson.annotations.SerializedName

data class AuthorDetailsDTO(
    @SerializedName("avatar_path") val avatarPath: String? = "",
    @SerializedName("name") val name: String? = "",
    @SerializedName("rating") val rating: Int? = 0,
    @SerializedName("username") val username: String? = ""
)