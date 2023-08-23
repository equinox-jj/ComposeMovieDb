package com.composemoviedb.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MovieAppBar(
    title: String,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back Icon",
                modifier = Modifier
                    .clickable { onBackButtonClicked() }
                    .padding(10.dp)
                    .clip(CircleShape),
            )
        },
        modifier = modifier,
    )
}