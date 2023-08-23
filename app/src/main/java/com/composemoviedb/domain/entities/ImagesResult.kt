package com.composemoviedb.domain.entities

data class ImagesResult(
    val backdrops: List<Any?> = listOf(),
    val logos: List<Any?> = listOf(),
    val posters: List<Any?> = listOf(),
)