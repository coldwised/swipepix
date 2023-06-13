package com.coldwised.swipepix.presentation.catalog.full_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.coldwised.swipepix.Constants
import com.coldwised.swipepix.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageScreenTopBar(
    isVisible: Boolean,
    favorite: Boolean,
    onFavoriteClicked: () -> Unit,
    onBackClicked: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            animationSpec = tween(Constants.TOP_BAR_VISIBILITY_ENTRY_ANIMATION_TIME)
        ),
        exit = fadeOut(
            animationSpec = tween(Constants.TOP_BAR_VISIBILITY_EXIT_ANIMATION_TIME)
        )
    ) {
        val iconsDefault = Icons.Default
        TopAppBar(
            title = {},
            actions = {
                IconButton(
                    onClick = onFavoriteClicked
                ) {
                    Icon(
                        contentDescription = stringResource(if(!favorite) R.string.add_to_favorites else R.string.remove_from_favorites),
                        imageVector = if(favorite) iconsDefault.Favorite else Icons.Default.FavoriteBorder
                    )
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = onBackClicked
                ) {
                    Icon(
                        contentDescription = stringResource(R.string.back_hint_text),
                        imageVector = iconsDefault.ArrowBack
                    )
                }
            },
            scrollBehavior = scrollBehavior
        )
    }
}