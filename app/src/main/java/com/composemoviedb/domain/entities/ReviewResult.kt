package com.composemoviedb.domain.entities

data class ReviewResult(
    val author: String? = "",
    val authorDetails: ReviewAuthor? = ReviewAuthor(),
    val content: String? = "",
    val createdAt: String? = "",
    val id: String? = "",
    val updatedAt: String? = "",
    val url: String? = "",
)