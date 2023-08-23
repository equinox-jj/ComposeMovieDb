package com.composemoviedb.data.mapper

import com.composemoviedb.data.datasource.remote.model.AuthorDetailsDTO
import com.composemoviedb.data.datasource.remote.model.CastDTO
import com.composemoviedb.data.datasource.remote.model.CreditsDTO
import com.composemoviedb.data.datasource.remote.model.GenreDTO
import com.composemoviedb.data.datasource.remote.model.ImagesDTO
import com.composemoviedb.data.datasource.remote.model.MovieDetailResponseDTO
import com.composemoviedb.data.datasource.remote.model.MovieDetailResultDTO
import com.composemoviedb.data.datasource.remote.model.MovieResponseDTO
import com.composemoviedb.data.datasource.remote.model.MovieResultDTO
import com.composemoviedb.data.datasource.remote.model.ReviewResponseDTO
import com.composemoviedb.data.datasource.remote.model.ReviewResultDTO
import com.composemoviedb.data.datasource.remote.model.VideosDTO
import com.composemoviedb.domain.entities.CastResult
import com.composemoviedb.domain.entities.CreditsResponse
import com.composemoviedb.domain.entities.GenreResult
import com.composemoviedb.domain.entities.ImagesResult
import com.composemoviedb.domain.entities.MovieDetailResponse
import com.composemoviedb.domain.entities.MovieListResponse
import com.composemoviedb.domain.entities.MovieListResult
import com.composemoviedb.domain.entities.ReviewAuthor
import com.composemoviedb.domain.entities.ReviewResponse
import com.composemoviedb.domain.entities.ReviewResult
import com.composemoviedb.domain.entities.VideoResponse
import com.composemoviedb.domain.entities.VideoResult

fun ReviewResponseDTO.toDomain(): ReviewResponse = ReviewResponse(
    id = id,
    page = page,
    results = results.mapNotNull { it?.toDomain() },
    totalPages = totalPages,
    totalResults = totalResults
)

fun ReviewResultDTO.toDomain(): ReviewResult = ReviewResult(
    author = author,
    authorDetails = authorDetails?.toDomain(),
    content = content,
    createdAt = createdAt,
    id = id,
    updatedAt = updatedAt,
    url = url
)

fun AuthorDetailsDTO.toDomain(): ReviewAuthor = ReviewAuthor(
    avatarPath = avatarPath,
    name = name,
    rating = rating,
    username = username
)

fun MovieResponseDTO.toDomain(): MovieListResponse = MovieListResponse(
    page = page,
    results = results.mapNotNull { it?.toDomain() }
)

fun MovieResultDTO.toDomain(): MovieListResult = MovieListResult(
    adult = adult,
    backdropPath = backdropPath,
    genreIds = genreIds,
    id = id,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun VideosDTO.toDomain(): VideoResponse = VideoResponse(
    results = results.mapNotNull { it?.toDomain() }
)

fun MovieDetailResultDTO.toDomain(): VideoResult = VideoResult(
    id = id,
    iso31661 = iso31661,
    iso6391 = iso6391,
    key = key,
    name = name,
    official = official,
    publishedAt = publishedAt,
    site = site,
    size = size,
    type = type,
)

fun MovieDetailResponseDTO.toDomain(): MovieDetailResponse = MovieDetailResponse(
    adult = adult,
    backdropPath = backdropPath,
    budget = budget,
    credits = credits?.toDomain(),
    genres = genres.mapNotNull { it?.toDomain() },
    homepage = homepage,
    id = id,
    images = images?.toDomain(),
    imdbId = imdbId,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    revenue = revenue,
    runtime = runtime,
    status = status,
    tagline = tagline,
    title = title,
    video = video,
    videos = videos?.toDomain(),
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun ImagesDTO.toDomain(): ImagesResult = ImagesResult(
    backdrops = backdrops,
    logos = logos,
    posters = posters
)

fun GenreDTO.toDomain(): GenreResult = GenreResult(
    id = id, name = name
)

fun CreditsDTO.toDomain(): CreditsResponse = CreditsResponse(
    cast = cast.mapNotNull { it?.toDomain() }
)

fun CastDTO.toDomain(): CastResult = CastResult(
    adult = adult,
    castId = castId,
    character = character,
    creditId = creditId,
    gender = gender,
    id = id,
    knownForDepartment = knownForDepartment,
    name = name,
    order = order,
    originalName = originalName,
    popularity = popularity,
    profilePath = profilePath,

    )