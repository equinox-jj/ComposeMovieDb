package com.composemoviedb.presentation.moviedetail.pagerscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.composemoviedb.domain.entities.ReviewResult
import com.composemoviedb.presentation.components.ReviewItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun ReviewScreen(data: Flow<PagingData<ReviewResult>>?, onRefresh: () -> Unit) {
    val lazyPagingItems = data?.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val showButton by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 0
        }
    }

    if (lazyPagingItems != null) {
        Scaffold(
            modifier = Modifier
                .fillMaxWidth()
                .height(610.dp)
                .padding(8.dp),
            floatingActionButton = {
                AnimatedVisibility(visible = showButton, enter = fadeIn(), exit = fadeOut()) {
                    FloatingActionButton(onClick = {
                        scope.launch { lazyListState.animateScrollToItem(0) }
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowUp,
                            contentDescription = "Top Button"
                        )
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    pagingRefreshState(pagingItems = lazyPagingItems, onRefresh = onRefresh)
                    pagingItems(pagingItems = lazyPagingItems)
                    pagingAppendLoadState(pagingItems = lazyPagingItems, onRefresh = onRefresh)
                }
            }
        }
    }
}

fun LazyListScope.pagingItems(pagingItems: LazyPagingItems<ReviewResult>) {
    items(count = pagingItems.itemCount) { index ->
        val data = pagingItems[index]

        data?.let {
            ReviewItem(
                data = it,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

fun LazyListScope.pagingAppendLoadState(
    pagingItems: LazyPagingItems<ReviewResult>,
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
    pagingItems: LazyPagingItems<ReviewResult>,
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