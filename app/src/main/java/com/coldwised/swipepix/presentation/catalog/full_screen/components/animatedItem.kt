package com.coldwised.swipepix.presentation.catalog.full_screen.components

import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.coldwised.swipepix.R
import com.coldwised.swipepix.presentation.catalog.full_screen.state.PagerScreenState
import com.coldwised.swipepix.presentation.catalog.full_screen.type.AnimationType
import com.skydoves.orbital.OrbitalScope
import com.skydoves.orbital.animateSharedElementTransition
import com.skydoves.orbital.rememberContentWithOrbitalScope

@Composable
fun animatedItem(
    imageUrl: String,
    animationType: AnimationType,
    pagerScreenState: PagerScreenState,
    paddingValues: PaddingValues,
    isRightLayoutDirection: Boolean,
) : @Composable() (OrbitalScope.() -> Unit) {
    return rememberContentWithOrbitalScope {
        val scope = this
        var isSuccess by remember {
            mutableStateOf(true)
        }
        val imageNotFoundId = remember { R.drawable.image_not_found }
        val imageWidth = remember { pagerScreenState.gridItemSize.width.dp }

        AsyncImage(
            modifier = if (animationType == AnimationType.EXPAND_ANIMATION) {
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(250.dp)
            } else {
                Modifier
                    .padding(
                        start = paddingValues.calculateLeftPadding(
                            if (isRightLayoutDirection)
                                LayoutDirection.Rtl else
                                LayoutDirection.Ltr
                        )
                    )
                    .offset { pagerScreenState.imageOffset }
                    .size(
                        width = imageWidth,
                        height = imageWidth - 20.dp
                    )
                    .padding(horizontal = 14.dp, vertical = 4.dp)
            }
                .animateSharedElementTransition(
                    scope,
                    SpringSpec(stiffness = 400f),
                    SpringSpec(stiffness = 400f)
                )
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
            ,
            onError = {
                isSuccess = false
            },
            placeholder = painterResource(id = imageNotFoundId),
            error = painterResource(id = imageNotFoundId),
            model = imageUrl,
            contentScale = ContentScale.Fit,
            contentDescription = null,
        )
    }
}