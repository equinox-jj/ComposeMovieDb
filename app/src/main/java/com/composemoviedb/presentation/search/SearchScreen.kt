package com.composemoviedb.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.composemoviedb.R
import com.composemoviedb.core.utils.UiEvent
import com.composemoviedb.presentation.components.MovieAppBar
import com.composemoviedb.presentation.components.MovieListCard

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToDetail: (Int) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val snackBarHostState = remember { SnackbarHostState() }
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.astronaut))

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.NavigateToDetailScreen -> {
                    navigateToDetail(uiEvent.movieId)
                }

                UiEvent.PopBackStack -> {
                    navigateBack()
                }

                is UiEvent.ShowErrorMessage -> {
                    val snackBarResult = snackBarHostState.showSnackbar(
                        message = uiEvent.message.toString(),
                        actionLabel = "Refresh",
                        duration = SnackbarDuration.Indefinite
                    )
                    when (snackBarResult) {
                        SnackbarResult.Dismissed -> {}
                        SnackbarResult.ActionPerformed -> {
                            viewModel.onEvent(SearchEvent.OnSearchQueryChanged(""))
                        }
                    }
                }

                else -> {}
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MovieAppBar(
                title = "Search Screen",
                onBackButtonClicked = { viewModel.onEvent(SearchEvent.OnBackButtonClicked) }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { viewModel.onEvent(SearchEvent.OnSearchQueryChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                placeholder = {
                    Text(text = "Search movie")
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search Icon")
                },
                singleLine = true,
                shape = RoundedCornerShape(20.dp),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus(true)
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (state.isLoading) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                if (state.movieResult?.isEmpty() == true && !state.isLoading) {
                    item {
                        LottieAnimation(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                            composition = composition,
                            iterations = LottieConstants.IterateForever
                        )
                        Text(
                            text = "Movie not found",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    items(
                        items = state.movieResult ?: emptyList(),
                        key = { it.id ?: 0 }
                    ) { data ->
                        MovieListCard(
                            data = data,
                            onMovieCardClicked = {
                                viewModel.onEvent(
                                    SearchEvent.OnSearchMovieCardClicked(data.id ?: 0)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    }
                }
                if (state.isError?.isNotEmpty() == true) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = state.isError.toString(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
        }
    }
}