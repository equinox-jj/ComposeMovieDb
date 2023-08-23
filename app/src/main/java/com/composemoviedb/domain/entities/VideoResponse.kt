package com.composemoviedb.domain.entities

data class VideoResponse(
    val results: List<VideoResult?> = listOf(),
)
