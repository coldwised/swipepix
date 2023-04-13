package com.coldwised.swipepix.presentation.gallery_screen.images_list.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.RectangleShape
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
import com.coldwised.swipepix.domain.model.OfferModel
import com.coldwised.swipepix.presentation.gallery_screen.images_list.event.GalleryScreenEvent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LazyGridImages(
    lazyGridState: LazyGridState,
    goodsList: ImmutableList<OfferModel>,
    onGalleryScreenEvent: (GalleryScreenEvent) -> Unit,
) {
    val context = LocalContext.current
    val gridItemModifier = Modifier
        .padding(1.dp)
        .fillMaxWidth()
        .height(220.dp)

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
            val contentScale = remember { ContentScale.FillBounds }
            val painter = rememberAsyncImagePainter(
                model = ImageRequest
                    .Builder(context)
                    .data(product.urlImageList[0])
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
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(onClick = {
                        val size = painter.intrinsicSize
                        val layoutInfo = lazyGridState.layoutInfo
                        val visibleItems = layoutInfo.visibleItemsInfo
                        val lastElement = visibleItems.last()
                        val currentGridItemOffset = visibleItems.find { it.index == index }?.offset ?: IntOffset.Zero
                        val offset = layoutInfo.viewportSize.height - lastElement.size.height
                        onGalleryScreenEvent(GalleryScreenEvent.OnSavePainterIntrinsicSize(size))
                        onGalleryScreenEvent(GalleryScreenEvent.OnSaveGridItemOffsetToScroll(offset))
                        onGalleryScreenEvent(GalleryScreenEvent.OnSaveCurrentGridItemOffset(currentGridItemOffset))
                        onGalleryScreenEvent(GalleryScreenEvent.OnImageClick(index))
                    })
                    .padding(6.dp)
                ,
            ) {
                LaunchedEffect(key1 = true) {
                    delay(500L)
                }
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp))
                    ,
                    painter = painter,
                    contentScale = contentScale,
                    contentDescription = null,
                )
                Row(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                        ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    var isInCart by rememberSaveable {
                        mutableStateOf(false)
                    }
                    Text(
                        text = stringResource(id = R.string.price_text, product.price),
                        style = MaterialTheme.typography.titleSmall
                    )
                    InputChip(
                        modifier = Modifier.height(28.dp),
                        selected  = isInCart,
                        onClick = { isInCart = !isInCart },
                        label = {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                painter = if(!isInCart) painterResource(id = R.drawable.ic_shopping_cart_24) else
                                    painterResource(id = R.drawable.ic_remove_shopping_cart_24),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                        ,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = stringResource(R.string.no_comments),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}