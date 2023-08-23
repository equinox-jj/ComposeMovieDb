package com.composemoviedb.presentation.discover

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.composemoviedb.R
import com.composemoviedb.core.utils.UiEvent
import com.composemoviedb.core.utils.getGenres
import com.composemoviedb.domain.entities.MovieListResult
import com.composemoviedb.presentation.components.MovieAppBar
import com.composemoviedb.presentation.components.MovieListCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToDetail: (Int) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lazyPagingItems = state.movieList.collectAsLazyPagingItems()
    val snackBarHostState = remember { SnackbarHostState() }
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val showButton by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 0
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.NavigateToDetailScreen -> {
                    navigateToDetail(uiEvent.movieId)
                }

                UiEvent.PopBackStack -> {
                    navigateBack()
                }

                else -> {}
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MovieAppBar(
                title = "Discover Screen",
                onBackButtonClicked = { viewModel.onEvent(DiscoverEvent.OnBackButtonClicked) },
                modifier = Modifier.fillMaxWidth()
            )
        },
        floatingActionButton = {
            AnimatedVisibility(visible = showButton, enter = fadeIn(), exit = fadeOut()) {
                FloatingActionButton(onClick = { scope.launch { lazyListState.animateScrollToItem(0) } }) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowUp,
                        contentDescription = "Top Button"
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.genres),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(items = state.genreList) {
                        FilterChip(
                            selected = false,
                            onClick = { },
                            label = {
                                Text(
                                    text = getGenres(it),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                )
                            },
                            enabled = false
                        )
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                state = lazyListState,
            ) {
                pagingRefreshState(
                    pagingItems = lazyPagingItems,
                    onRefresh = { viewModel.onEvent(DiscoverEvent.OnDiscoverMovieLoaded) }
                )
                pagingItems(
                    pagingItems = lazyPagingItems,
                    navigateToDetail = viewModel::onEvent,
                )
                pagingAppendLoadState(
                    pagingItems = lazyPagingItems,
                    onRefresh = { viewModel.onEvent(DiscoverEvent.OnDiscoverMovieLoaded) }
                )
            }
        }
    }
}

fun LazyListScope.pagingItems(
    pagingItems: LazyPagingItems<MovieListResult>,
    navigateToDetail: (DiscoverEvent) -> Unit,
) {
    items(
        count = pagingItems.itemCount,
    ) { index ->
        val data = pagingItems[index]

        data?.let {
            MovieListCard(
                data = it,
                onMovieCardClicked = { movieId ->
                    navigateToDetail(DiscoverEvent.OnMovieCardClicked(movieId))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}

fun LazyListScope.pagingAppendLoadState(
    pagingItems: LazyPagingItems<MovieListResult>,
    onRefresh: () -> Unit,
) {
    when (val loadState = pagingItems.loadState.append) {
        is LoadState.Error -> {
            item {
                val errorMessage = loadState.error.message ?: ""
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = errorMessage,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(onClick = onRefresh) {
                        Text(text = "Refresh")
                    }
                }
            }
        }

        LoadState.Loading -> {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        else -> {}
    }
}

fun LazyListScope.pagingRefreshState(
    pagingItems: LazyPagingItems<MovieListResult>,
    onRefresh: () -> Unit,
) {
    when (val loadState = pagingItems.loadState.refresh) {
        is LoadState.Error -> {
            item {
                val errorMessage = loadState.error.message ?: ""
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = errorMessage,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(onClick = onRefresh) {
                        Text(text = "Refresh")
                    }
                }
            }
        }

        LoadState.Loading -> {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        else -> {}
    }
}