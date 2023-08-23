package com.composemoviedb.presentation.moviedetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.composemoviedb.R
import com.composemoviedb.core.utils.Constants
import com.composemoviedb.core.utils.UiEvent
import com.composemoviedb.presentation.moviedetail.pagerscreen.DescriptionScreen
import com.composemoviedb.presentation.moviedetail.pagerscreen.ReviewScreen

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val tabs = listOf("Description", "Review")
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) { tabs.size }
    val scrollState = rememberScrollState()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                UiEvent.PopBackStack -> {
                    navigateBack()
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
                            viewModel.onEvent(MovieDetailEvent.OnMovieDetailLoaded)
                            viewModel.onEvent(MovieDetailEvent.OnReviewLoaded)
                        }
                    }
                }

                else -> {}
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValues ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        if (state.movieDetailData != null && state.reviewList != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .verticalScroll(scrollState),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(Constants.COVER_MEDIUM + state.movieDetailData?.backdropPath)
                            .crossfade(1000)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .build(),
                        contentDescription = "BackDrop Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(6.dp),
                        contentScale = ContentScale.FillBounds
                    )
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Back Icon",
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(20.dp)
                            .background(color = Color.White, shape = CircleShape)
                            .padding(5.dp)
                            .clip(CircleShape)
                            .clickable { viewModel.onEvent(MovieDetailEvent.OnBackButtonClicked) },
                        tint = Color.Gray
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black
                                    ),
                                    startY = 250f,
                                ),
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(Constants.COVER_EXTRA_SMALL + state.movieDetailData?.posterPath)
                                    .crossfade(1000)
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                                    .build(),
                                contentDescription = "Poster Image",
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(RoundedCornerShape(15.dp))
                                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(15.dp)),
                                contentScale = ContentScale.FillBounds
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = state.movieDetailData?.title.toString(),
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 2,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                color = Color.White
                            )
                        }
                    }
                }
                TabRow(
                    selectedTabIndex = state.tabIndex,
                    modifier = Modifier.fillMaxWidth(),
                    indicator = { tabPosition ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier
                                .tabIndicatorOffset(tabPosition[state.tabIndex])
                                .padding(horizontal = 25.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                    },
                    divider = { Divider() },
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = { viewModel.onEvent(MovieDetailEvent.OnTabClicked(index)) }
                        ) {
                            Text(text = title, modifier = Modifier.padding(10.dp))
                        }
                    }
                }
                when (state.tabIndex) {
                    0 -> DescriptionScreen(data = state.movieDetailData)
                    1 -> ReviewScreen(
                        data = state.reviewList,
                        onRefresh = { viewModel.onEvent(MovieDetailEvent.OnReviewLoaded) }
                    )
                }
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