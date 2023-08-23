package com.composemoviedb.domain.entities

data class CreditsResponse(
    val cast: List<CastResult?> = listOf(),
)
