package com.coldwised.swipepix.presentation.catalog.full_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.coldwised.swipepix.R
import com.coldwised.swipepix.data.remote.dto.ProductDto
import com.coldwised.swipepix.presentation.catalog.full_screen.type.AnimationType
import com.coldwised.swipepix.ui.theme.emptyStarbarColor

@Composable
fun OfferDetails(
    image: @Composable() (() -> Unit),
    product: ProductDto,
    animationType: AnimationType,
    modifier: Modifier,
) {
    val verticalScroll = rememberScrollState()
    var fabExtended by remember {
        mutableStateOf(animationType == AnimationType.EXPAND_ANIMATION)
    }
    LaunchedEffect(key1 = animationType) {
        fabExtended = animationType == AnimationType.EXPAND_ANIMATION
    }
    LaunchedEffect(verticalScroll) {
        var prev = 0
        snapshotFlow { verticalScroll.value }
            .collect {
                fabExtended = it <= prev
                prev = it
            }
    }
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(verticalScroll)
        ) {
            image()
            val textVisible = animationType == AnimationType.EXPAND_ANIMATION
            AnimatedVisibility(
                visible = textVisible,
                modifier = Modifier
                    .padding(top = 12.dp)
                ,
                enter = slideInVertically(
                    animationSpec = tween(400),
                    initialOffsetY = {
                        it / 2
                    }
                ),
            ) {
                if(!textVisible) return@AnimatedVisibility
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 8.dp)
                        ,
                        text = product.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                        ,
                        text = stringResource(id = R.string.price_text, product.price).replace(',', ' '),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier
                            .padding(bottom = 6.dp)
                            .fillMaxWidth()
                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        for(i in 0..4) {
                            Icon(
                                modifier = Modifier
                                .size(16.dp)
                                ,
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.emptyStarbarColor
                            )
                        }
                    }
                    Text(
                        modifier = Modifier.padding(bottom = 22.dp),
                        text = stringResource(R.string.no_comments),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.emptyStarbarColor,
                    )
                    product.description?.let { description ->
                        Text(
                            modifier = Modifier.padding(bottom = 16.dp),
                            text = stringResource(R.string.product_description),
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Text(
                            text = description
                        )
                    }
                    if(product.params.isEmpty()) {
                        return@AnimatedVisibility
                    }
                    Text(
                        modifier = Modifier.padding(bottom = 16.dp),
                        text = stringResource(R.string.offer_сharacteristics_text),
                        style = MaterialTheme.typography.titleLarge,
                    )
                    for(param in product.params) {
                        Row(
                            Modifier
                                .padding(bottom = 12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(end = 6.dp)
                                    .weight(0.5f)
                                        ,
                                text = param.first,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.emptyStarbarColor
                            )
                            Text(
                                modifier = Modifier
                                    .weight(0.5f)
                                ,
                                text = param.second,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
        AnimatedVisibility(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter)
            ,
            visible = fabExtended,
            enter = slideInVertically(
                animationSpec = tween(300),
                initialOffsetY = {
                    it * 2
                }
            ),
            exit = slideOutVertically(
                animationSpec = tween(300),
                targetOffsetY = {
                    it * 2
                }
            )
        ) {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp)
                ,
                shape = RoundedCornerShape(6.dp),
                content = {
                    Text(
                        text = stringResource(R.string.offer_cart_button_text, product.price).replace(',', ' '),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                onClick = { /*TODO*/ }
            )
        }
    }
}