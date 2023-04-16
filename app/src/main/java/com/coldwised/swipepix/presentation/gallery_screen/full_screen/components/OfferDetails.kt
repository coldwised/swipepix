package com.coldwised.swipepix.presentation.gallery_screen.full_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.coldwised.swipepix.R
import kotlinx.coroutines.delay

@Composable
fun OfferDetails(
    image: @Composable() (() -> Unit),
    price: Float,
    name: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        image()
        val isVisible = remember {
            MutableTransitionState(false)
        }
        LaunchedEffect(key1 = true) {
            delay(100L)
            isVisible.apply {
                // Start the animation immediately.
                this.targetState = true
            }
        }
        AnimatedVisibility(
            visibleState = isVisible,
            enter = slideInVertically(
                animationSpec = tween(300),
                initialOffsetY = {
                    it * 2
                }
            )
        ) {
            Column {
                Text(
                    text = name,
                )
                Text(
                    text = stringResource(id = R.string.price_text, price)
                )
                Text(
                    text = stringResource(id = R.string.no_comments)
                )
            }
        }
    }
}