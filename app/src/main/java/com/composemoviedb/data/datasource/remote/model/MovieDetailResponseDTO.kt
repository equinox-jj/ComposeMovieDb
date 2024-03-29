package com.composemoviedb.data.datasource.remote.model


import com.google.gson.annotations.SerializedName

data class MovieDetailResponseDTO(
    @SerializedName("adult") val adult: Boolean? = false,
    @SerializedName("backdrop_path") val backdropPath: String? = "",
    @SerializedName("belongs_to_collection") val belongsToCollection: BelongsToCollectionDTO? = BelongsToCollectionDTO(),
    @SerializedName("budget") val budget: Int? = 0,
    @SerializedName("credits") val credits: CreditsDTO? = CreditsDTO(),
    @SerializedName("genres") val genres: List<GenreDTO?> = listOf(),
    @SerializedName("homepage") val homepage: String? = "",
    @SerializedName("id") val id: Int? = 0,
    @SerializedName("images") val images: ImagesDTO? = ImagesDTO(),
    @SerializedName("imdb_id") val imdbId: String? = "",
    @SerializedName("original_language") val originalLanguage: String? = "",
    @SerializedName("original_title") val originalTitle: String? = "",
    @SerializedName("overview") val overview: String? = "",
    @SerializedName("popularity") val popularity: Double? = 0.0,
    @SerializedName("poster_path") val posterPath: String? = "",
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompanyDTO?> = listOf(),
    @SerializedName("production_countries") val productionCountries: List<ProductionCountryDTO?> = listOf(),
    @SerializedName("release_date") val releaseDate: String? = "",
    @SerializedName("revenue") val revenue: Int? = 0,
    @SerializedName("runtime") val runtime: Int? = 0,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguageDTO?> = listOf(),
    @SerializedName("status") val status: String? = "",
    @SerializedName("tagline") val tagline: String? = "",
    @SerializedName("title") val title: String? = "",
    @SerializedName("video") val video: Boolean? = false,
    @SerializedName("videos") val videos: VideosDTO? = VideosDTO(),
    @SerializedName("vote_average") val voteAverage: Double? = 0.0,
    @SerializedName("vote_count") val voteCount: Int? = 0,
)