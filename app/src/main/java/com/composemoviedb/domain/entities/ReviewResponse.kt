package com.composemoviedb.domain.entities

data class ReviewResponse(
    val id: Int? = 0,
    val page: Int? = 0,
    val results: List<ReviewResult?> = listOf(),
    val totalPages: Int? = 0,
    val totalResults: Int? = 0,
)
