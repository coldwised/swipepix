package com.coldwised.swipepix.presentation.favorites.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.coldwised.swipepix.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteTopBar(
    onThemeSettingsClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.favorites_topbar_title),
                style = MaterialTheme.typography.titleMedium,
            )
        },
        scrollBehavior = scrollBehavior,
        actions = {
            Column {
                var menuVisible by remember {
                    mutableStateOf(false)
                }
                IconButton(
                    onClick = {
                        menuVisible = !menuVisible
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(R.string.more_text),
                    )
                }
                DropdownMenu(
                    expanded = menuVisible,
                    onDismissRequest = {
                        menuVisible = false
                    }
                ) {
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxSize(),
                        text = {
                            Text(
                                text = stringResource(R.string.theme_label_text)
                            )
                        },
                        onClick = onThemeSettingsClick
                    )
                }
            }
        }
    )
}