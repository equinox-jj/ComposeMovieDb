package com.composemoviedb.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.composemoviedb.R
import com.composemoviedb.core.utils.Constants.BASE_TRAILER_THUMBNAIL
import com.composemoviedb.core.utils.Constants.BASE_TRAILER_THUMBNAIL_END
import com.composemoviedb.domain.entities.VideoResult

@Composable
internal fun TrailerItem(
    data: VideoResult,
    onTrailerClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Card(modifier = modifier.clickable { onTrailerClicked() }, shape = RoundedCornerShape(20.dp)) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(BASE_TRAILER_THUMBNAIL + data.key + BASE_TRAILER_THUMBNAIL_END)
                    .crossfade(1000)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .build(),
                contentDescription = "Trailer Poster",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
            )
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Play Icon",
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(color = Color.Red, shape = RoundedCornerShape(15.dp))
                    .padding(15.dp),
                tint = Color.White,
            )
        }
    }
}