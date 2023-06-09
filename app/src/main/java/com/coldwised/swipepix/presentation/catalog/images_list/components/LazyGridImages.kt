package com.coldwised.swipepix.presentation.catalog.images_list.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.coldwised.swipepix.R
import com.coldwised.swipepix.data.remote.dto.ProductDto
import com.coldwised.swipepix.presentation.catalog.images_list.event.GalleryScreenEvent
import com.coldwised.swipepix.ui.theme.emptyStarbarColor
import com.coldwised.swipepix.ui.theme.productCardColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LazyGridImages(
    lazyGridState: LazyGridState,
    goodsList: List<ProductDto>,
    onGalleryScreenEvent: (GalleryScreenEvent) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current
    val gridItemModifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyGridState,
        columns = GridCells.Adaptive(110.dp),
    ) {
        itemsIndexed(
            goodsList
        ) { index, product ->
            var isSuccess by rememberSaveable {
                mutableStateOf(true)
            }
            val imageNotFoundId = remember { R.drawable.image_not_found }
            val contentScale = remember { ContentScale.Fit }
            val painter = rememberAsyncImagePainter(
                model = ImageRequest
                    .Builder(context)
                    .data(product.images[0])
                    .size(coil.size.Size.ORIGINAL)
                    .error(imageNotFoundId)
                    .placeholder(
                        if(isSuccess)
                            R.drawable.placeholder else {
                            imageNotFoundId
                        }
                    )
                    .build(),
                filterQuality = FilterQuality.None,
                contentScale = contentScale,
                onState = { imageState ->
                    when(imageState) {
                        is AsyncImagePainter.State.Error -> {
                            isSuccess = false
                        }
                        is AsyncImagePainter.State.Success -> {
                            isSuccess = true
                        }
                        else -> {}
                    }
                }
            )
            Column(
                modifier = gridItemModifier
                    .shadow(shape = RoundedCornerShape(12.dp), elevation = 1.dp, clip = true)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.productCardColor)
                    .clickable(onClick = {
                        val size = painter.intrinsicSize
                        val layoutInfo = lazyGridState.layoutInfo
                        val visibleItems = layoutInfo.visibleItemsInfo
                        val lastElement = visibleItems.last()
                        val currentGridItemOffset =
                            visibleItems.find { it.index == index }?.offset ?: IntOffset.Zero
                        val offset = layoutInfo.viewportSize.height - lastElement.size.height
                        onGalleryScreenEvent(GalleryScreenEvent.OnSavePainterIntrinsicSize(size))
                        onGalleryScreenEvent(
                            GalleryScreenEvent.OnSaveGridItemOffsetToScroll(
                                offset
                            )
                        )
                        onGalleryScreenEvent(
                            GalleryScreenEvent.OnSaveCurrentGridItemOffset(
                                currentGridItemOffset
                            )
                        )
                        onGalleryScreenEvent(GalleryScreenEvent.OnImageClick(index))
                    },
                        interactionSource = interactionSource,
                        indication = null
                    ),
            ) {
                Image(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp))
                    ,
                    painter = painter,
                    contentScale = contentScale,
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp, start = 10.dp, end = 10.dp),
                    text = stringResource(id = R.string.price_text, product.price).replace(',', ' '),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp, start = 10.dp, end = 10.dp)
                    ,
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(end = 2.dp)
                            .size(14.dp),
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.emptyStarbarColor
                    )
                    Text(
                        text = stringResource(R.string.no_comments),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.emptyStarbarColor,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(bottom = 12.dp, start = 10.dp, end = 10.dp),
                    maxLines = 3,
                    minLines = 3
                )
                CustomOutlinedChip(
                    selected = product.inCart,
                    modifier = Modifier
                        .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                        .height(30.dp)
                    ,
                    onClick = { onGalleryScreenEvent(GalleryScreenEvent.OnCartClick(product.id)) }
                )
            }
        }
    }
}

@Composable
fun CustomOutlinedChip(
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    OutlinedButton(
        contentPadding = PaddingValues(0.dp),
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (!selected) MaterialTheme.colorScheme.primary else Color.Transparent,
            contentColor = if (!selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(9.dp),
        modifier = modifier,
        content = {
            Text(
                text = if (!selected) stringResource(R.string.add_to_cart_text) else
                    stringResource(R.string.remove_from_cart_text),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(5.dp))
            if (selected) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = R.drawable.ic_remove_shopping_cart_24),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
            }
        }
    )
}