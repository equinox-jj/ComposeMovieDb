package com.composemoviedb.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.composemoviedb.core.destination.MainNavDestination
import com.composemoviedb.core.utils.Constants.GENRE_LIST_ARGS
import com.composemoviedb.core.utils.Constants.MOVIE_ID_ARGS
import com.composemoviedb.presentation.discover.DiscoverScreen
import com.composemoviedb.presentation.genrelist.GenreListScreen
import com.composemoviedb.presentation.moviedetail.MovieDetailScreen
import com.composemoviedb.presentation.search.SearchScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = MainNavDestination.GenreListScreen.route) {
            GenreListScreen(
                navigateToSearch = {
                    navController.navigate(MainNavDestination.SearchScreen.route)
                },
                navigateToDiscover = { genreList ->
                    navController.navigate(MainNavDestination.DiscoverScreen.passGenreList(genreList))
                }
            )
        }
        composable(
            route = MainNavDestination.DiscoverScreen.route,
            arguments = listOf(
                navArgument(name = GENRE_LIST_ARGS) {
                    type = NavType.StringType
                }
            )
        ) {
            DiscoverScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToDetail = { movieId ->
                    navController.navigate(MainNavDestination.MovieDetailScreen.passMovieId(movieId))
                }
            )
        }
        composable(
            route = MainNavDestination.MovieDetailScreen.route,
            arguments = listOf(
                navArgument(name = MOVIE_ID_ARGS) {
                    type = NavType.IntType
                }
            )
        ) {
            MovieDetailScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(route = MainNavDestination.SearchScreen.route) {
            SearchScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToDetail = { movieId ->
                    navController.navigate(MainNavDestination.MovieDetailScreen.passMovieId(movieId))
                }
            )
        }
    }
}