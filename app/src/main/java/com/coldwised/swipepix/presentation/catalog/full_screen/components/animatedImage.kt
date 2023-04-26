package com.coldwised.swipepix.presentation.catalog.full_screen.components

import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun animatedImage(
    imageUrl: String,
    animationType: AnimationType,
    pagerScreenState: PagerScreenState,
    paddingValues: PaddingValues,
    isHorizontalOrientation: Boolean,
    isRightLayoutDirection: Boolean,
) : @Composable() (OrbitalScope.() -> Unit){
    return rememberContentWithOrbitalScope {
        val orbitalScope = this
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val painterIntrinsicSize = pagerScreenState.painterIntrinsicSize
            val ratio = painterIntrinsicSize.width/painterIntrinsicSize.height
            var isSuccess by remember {
                mutableStateOf(true)
            }
            val imageNotFoundId = R.drawable.image_not_found
            AsyncImage(
                modifier = if (animationType == AnimationType.EXPAND_ANIMATION) {
                    Modifier
                        .fillMaxSize(pagerScreenState.currentScale)
                        .aspectRatio(ratio, isHorizontalOrientation)
                        .align(Alignment.Center)
                } else {
                    Modifier
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            start = paddingValues.calculateLeftPadding(
                                if (isRightLayoutDirection)
                                    LayoutDirection.Rtl else
                                    LayoutDirection.Ltr
                            )
                        )
                        .offset { pagerScreenState.imageOffset }
                        .size(
                            pagerScreenState.gridItemSize.width.dp,
                        )
                        .padding(1.dp)
                }.animateSharedElementTransition(
                    orbitalScope,
                    SpringSpec(stiffness = 1200f),
                    SpringSpec(stiffness = 1200f)
                ),
                onError = {
                    isSuccess = false
                },
                placeholder = painterResource(id = imageNotFoundId),
                error = painterResource(id = imageNotFoundId),
                model = imageUrl,
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
            )
        }
    }
}