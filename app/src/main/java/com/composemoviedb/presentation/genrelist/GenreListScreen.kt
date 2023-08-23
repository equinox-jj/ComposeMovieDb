package com.composemoviedb.presentation.genrelist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composemoviedb.R
import com.composemoviedb.core.utils.UiEvent

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun GenreListScreen(
    viewModel: GenreListViewModel = hiltViewModel(),
    navigateToSearch: () -> Unit,
    navigateToDiscover: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val selectedItems = remember { mutableStateListOf<String>() }
    val scrollState = rememberScrollState()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                UiEvent.NavigateToDiscoverScreen -> {
                    navigateToDiscover(selectedItems.joinToString())
                }

                UiEvent.NavigateToSearchScreen -> {
                    navigateToSearch()
                }

                is UiEvent.ShowErrorMessage -> {
                    val snackBarResult = snackBarHostState.showSnackbar(
                        message = uiEvent.message.toString(),
                        actionLabel = "Refresh",
                        withDismissAction = false,
                        duration = SnackbarDuration.Indefinite,
                    )
                    when (snackBarResult) {
                        SnackbarResult.Dismissed -> {}

                        SnackbarResult.ActionPerformed -> {
                            viewModel.onEvent(GenreListEvent.OnGenreListLoaded)
                        }
                    }
                }

                is UiEvent.ShowGenreErrorMessage -> {
                    snackBarHostState.showSnackbar(
                        message = uiEvent.message.toString(),
                        withDismissAction = true,
                        duration = SnackbarDuration.Short,
                    )
                }

                else -> {}
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            if (!state.isLoading && state.isError == null) {
                FloatingActionButton(onClick = { viewModel.onEvent(GenreListEvent.OnSearchClicked) }) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Icon")
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .padding(12.dp)
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ) {
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            if (state.genreList?.isNotEmpty() == true) {
                Text(
                    text = stringResource(R.string.select_movie_genres),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    maxLines = 2,
                )
                Spacer(modifier = Modifier.height(20.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    maxItemsInEachRow = 3,
                ) {
                    state.genreList?.forEach { data ->
                        ElevatedFilterChip(
                            selected = selectedItems.contains(data.id.toString()),
                            onClick = {
                                viewModel.onEvent(
                                    GenreListEvent.GenreChipClicked(
                                        selectedChip = selectedItems.joinToString(),
                                        isChipSelected = if (selectedItems.contains(data.id.toString())) {
                                            selectedItems.remove(data.id.toString())
                                        } else {
                                            selectedItems.add(data.id.toString())
                                        }
                                    )
                                )
                            },
                            label = {
                                Text(
                                    text = data.name.orEmpty(),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                )
                            },
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(horizontal = 4.dp),
                            leadingIcon = {
                                AnimatedVisibility(
                                    visible = selectedItems.contains(data.id.toString()),
                                    enter = expandIn(),
                                    exit = shrinkOut()
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = "Checked Chip"
                                    )
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Button(
                    onClick = {
                        if (selectedItems.isNotEmpty()) {
                            viewModel.onEvent(GenreListEvent.OnSubmitClicked)
                        } else {
                            viewModel.onEvent(GenreListEvent.ShowErrorMessage("Select genre first"))
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(text = stringResource(R.string.submit), fontSize = 16.sp)
                }
            }
            if (state.isError?.isNotEmpty() == true) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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