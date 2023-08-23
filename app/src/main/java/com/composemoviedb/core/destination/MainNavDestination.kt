package com.composemoviedb.core.destination

import com.composemoviedb.core.utils.Constants.GENRE_LIST_ARGS
import com.composemoviedb.core.utils.Constants.MOVIE_ID_ARGS

sealed class MainNavDestination(val route: String) {
    data object DiscoverScreen : MainNavDestination(route = "discover_screen/{$GENRE_LIST_ARGS}") {
        fun passGenreList(genreList: String) = "discover_screen/$genreList"
    }

    data object SearchScreen : MainNavDestination(route = "search_screen")
    data object GenreListScreen : MainNavDestination(route = "genre_list_screen")
    data object MovieDetailScreen : MainNavDestination(
        route = "movie_detail_screen/{$MOVIE_ID_ARGS}"
    ) {
        fun passMovieId(movieId: Int) = "movie_detail_screen/$movieId"
    }
}
