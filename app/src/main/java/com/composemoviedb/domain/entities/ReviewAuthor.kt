package com.composemoviedb.domain.entities

data class ReviewAuthor(
    val avatarPath: String? = "",
    val name: String? = "",
    val rating: Int? = 0,
    val username: String? = "",
)