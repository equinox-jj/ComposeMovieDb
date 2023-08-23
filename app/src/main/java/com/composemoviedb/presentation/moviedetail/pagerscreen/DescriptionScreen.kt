package com.composemoviedb.presentation.moviedetail.pagerscreen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composemoviedb.R
import com.composemoviedb.core.utils.Constants.BASE_TRAILER_URL
import com.composemoviedb.core.utils.Constants.BASE_TRAILER_URL_APP
import com.composemoviedb.domain.entities.MovieDetailResponse
import com.composemoviedb.presentation.components.CastItem
import com.composemoviedb.presentation.components.TrailerItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionScreen(data: MovieDetailResponse?) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var isExpanded by remember { mutableStateOf(false) }

    if (data != null) {
        Scaffold(
            modifier = Modifier
                .fillMaxWidth()
                .height(610.dp)
                .padding(8.dp)
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .verticalScroll(state = scrollState)
            ) {
                if (data.releaseDate?.isNotEmpty() == true) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Release date: ${data.releaseDate}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            fontStyle = FontStyle.Italic,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (data.genres.mapNotNull { it }.isNotEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Genre",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(items = data.genres) { genreResult ->
                                genreResult?.let {
                                    FilterChip(
                                        selected = false,
                                        onClick = { },
                                        label = {
                                            Text(
                                                text = it.name.toString(),
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                            )
                                        },
                                        enabled = false
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Movie Overview",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = if (data.overview?.isNotEmpty() == true) data.overview
                    else stringResource(R.string.no_description),
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                        .clickable { isExpanded = !isExpanded },
                    textAlign = TextAlign.Start,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                    fontSize = 13.sp,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(10.dp))
                if (data.credits?.cast?.mapNotNull { it }?.isNotEmpty() == true) {
                    Text(
                        text = "Top Billed Cast",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val castResult = data.credits.cast.mapNotNull { it }

                        items(items = castResult) {
                            CastItem(
                                data = it,
                                modifier = Modifier.width(100.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (data.videos?.results?.mapNotNull { it }?.isNotEmpty() == true) {
                    Text(
                        text = "Trailer",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val videoResult = data.videos.results.mapNotNull { it }

                        items(items = videoResult) {
                            TrailerItem(
                                data = it,
                                onTrailerClicked = {
                                    val appIntent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(BASE_TRAILER_URL_APP + it.key)
                                    )
                                    val webIntent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(BASE_TRAILER_URL + it.key)
                                    )

                                    try {
                                        context.startActivity(appIntent)
                                    } catch (e: ActivityNotFoundException) {
                                        context.startActivity(webIntent)
                                    }
                                },
                                modifier = Modifier
                                    .height(200.dp)
                                    .width(250.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}