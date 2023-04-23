package com.coldwised.swipepix.presentation.gallery.full_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.coldwised.swipepix.R
import com.coldwised.swipepix.domain.model.OfferModel
import com.coldwised.swipepix.presentation.gallery.full_screen.type.AnimationType
import com.coldwised.swipepix.ui.theme.emptyStarbarColor

@Composable
fun OfferDetails(
    image: @Composable() (() -> Unit),
    offer: OfferModel,
    animationType: AnimationType
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        image()
        val textVisible = animationType == AnimationType.EXPAND_ANIMATION
        AnimatedVisibility(
            visible = textVisible,
            modifier = Modifier
                .padding(top = 12.dp)
                ,
            enter = slideInVertically(
                animationSpec = tween(300),
                initialOffsetY = {
                    it * 2
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
                    text = offer.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                    ,
                    text = stringResource(id = R.string.price_text, offer.price),
                    style = MaterialTheme.typography.titleLarge
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(end = 6.dp)
                            //.size(30.dp)
                            ,
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.emptyStarbarColor
                    )
                    Text(
                        text = stringResource(R.string.no_comments),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.emptyStarbarColor,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = stringResource(R.string.offer_сharacteristics_text),
                    style = MaterialTheme.typography.titleMedium
                )
                for(param in offer.params) {
                    Row(
                        Modifier
                            .padding(bottom = 12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = param.name,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.emptyStarbarColor
                        )
                        Spacer(modifier = Modifier.width(70.dp))
                        Text(
                            text = param.value,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }
        }
    }
}