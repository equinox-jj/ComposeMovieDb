package com.composemoviedb.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.composemoviedb.R
import com.composemoviedb.domain.entities.ReviewResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ReviewItem(
    data: ReviewResult,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var isExpanded by remember {
        mutableStateOf(false)
    }

    OutlinedCard(onClick = { isExpanded = !isExpanded }, modifier = modifier) {
        Box(modifier = Modifier.wrapContentSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(data.authorDetails?.avatarPath)
                        .crossfade(1000)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .build(),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .padding(bottom = 30.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    if (data.updatedAt?.isNotEmpty() == true) {
                        Text(
                            text = data.updatedAt,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Light,
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = if (data.authorDetails?.name?.isNotEmpty() == true) data.authorDetails.name else "Anonymous",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    if (data.content?.isNotEmpty() == true) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Review: ",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = data.content,
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize(),
                            textAlign = TextAlign.Start,
                            maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 13.sp
                        )
                    }
                }
            }
            Icon(
                imageVector = if (isExpanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .background(color = Color.Gray, shape = CircleShape)
                    .padding(4.dp)
            )
        }
    }
}