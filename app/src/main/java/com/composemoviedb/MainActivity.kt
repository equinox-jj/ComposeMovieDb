package com.composemoviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.composemoviedb.core.destination.MainNavDestination
import com.composemoviedb.navigation.SetupNavGraph
import com.composemoviedb.theme.ComposeMovieDbTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMovieDbTheme {
                val navController = rememberNavController()

                SetupNavGraph(
                    navController = navController,
                    startDestination = MainNavDestination.GenreListScreen.route,
                )
            }
        }
    }
}