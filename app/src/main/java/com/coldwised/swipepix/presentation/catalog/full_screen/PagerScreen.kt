package com.coldwised.swipepix.presentation.catalog.full_screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.coldwised.swipepix.R
import com.coldwised.swipepix.data.remote.dto.ProductDto
import com.coldwised.swipepix.presentation.catalog.full_screen.components.*
import com.coldwised.swipepix.presentation.catalog.full_screen.event.ImageScreenEvent
import com.coldwised.swipepix.presentation.catalog.full_screen.state.PagerScreenState
import com.coldwised.swipepix.presentation.catalog.full_screen.type.AnimationType
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.skydoves.orbital.Orbital
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PagerScreen(
    products: List<ProductDto>,
    pagerScreenState: PagerScreenState,
    paddingValues: PaddingValues,
    onImageScreenEvent: (ImageScreenEvent) -> Unit,
) {
    if(!pagerScreenState.isVisible || products.isEmpty()) {
        return
    }
    BackHandler {
        onImageScreenEvent(ImageScreenEvent.OnBackToGallery)
    }
    val systemUiController = rememberSystemUiController()
    val systemNavigationBarVisible = pagerScreenState.systemNavigationBarVisible
    LaunchedEffect(key1 = systemNavigationBarVisible) {
        systemUiController.isNavigationBarVisible = systemNavigationBarVisible
    }
    val pagerIndex = pagerScreenState.pagerIndex
    val pagerState = rememberPagerState(
        initialPage = pagerIndex
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val transparentColor = remember {
        Color.Transparent
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val topBarVisible = pagerScreenState.topBarVisible
    val offer = products[pagerIndex]
    val imageUrl = offer.images[0]
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
        ,
        containerColor = transparentColor,
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            ImageScreenTopBar(
                isVisible = topBarVisible,
                title = pagerScreenState.topBarText,
                onBackClicked = {
                    onImageScreenEvent(ImageScreenEvent.OnBackToGallery)
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        val localConfiguration = LocalConfiguration.current
        val isHorizontalOrientation = localConfiguration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val isRightLayoutDirection = localConfiguration.layoutDirection == Configuration.SCREENLAYOUT_LAYOUTDIR_RTL
        val animationState = pagerScreenState.animationState
        val stateAnimationType = animationState.animationType
        val expandAnimationType = remember { AnimationType.EXPAND_ANIMATION }
        LaunchedEffect(key1 = true) {
            if(stateAnimationType != expandAnimationType) {
                onImageScreenEvent(ImageScreenEvent.OnBarsVisibilityChange)
                onImageScreenEvent(ImageScreenEvent.OnAnimate(expandAnimationType))
            }
        }

        var animationType by remember {
            mutableStateOf(stateAnimationType)
        }
        LaunchedEffect(key1 = stateAnimationType) {
            animationType = stateAnimationType
        }
        val currentPage = pagerState.currentPage
        LaunchedEffect(key1 = currentPage) {
            onImageScreenEvent(
                ImageScreenEvent.OnPagerIndexChanged(
                    currentPage
                ))
        }
        val imageContent = animatedItem(
            pagerScreenState = pagerScreenState,
            imageUrl = imageUrl,
            isRightLayoutDirection = isRightLayoutDirection,
            paddingValues = paddingValues,
            animationType = animationType,
        )
        val backGroundColor by animateColorAsState(
            targetValue = if (animationType == expandAnimationType) MaterialTheme.colorScheme.background else transparentColor,
            animationSpec = tween(durationMillis = 270)
        )
        Box(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .background(backGroundColor)
                .fillMaxSize()
        ) {
            if(animationState.isAnimationInProgress) {
                Orbital(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    OfferDetails(
                        image = { imageContent() },
                        product = offer,
                        animationType = animationType,
                        modifier = Modifier.fillMaxSize(),
                        onAddToCartButtonClick = {}
                    )
                }
            } else if(animationType == expandAnimationType) {
                // var isTouching by remember {
                //     mutableStateOf(false)
                // }
                val imageListSize = products.size
                HorizontalPager(
                    state = pagerState,
                    pageCount = imageListSize,
                    modifier = Modifier
                        .fillMaxSize(),
                    pageSpacing = 16.dp,
                    flingBehavior = PagerDefaults.flingBehavior(
                        state = pagerState,
                        pagerSnapDistance = PagerSnapDistance.atMost(0)
                    ),
                    contentPadding = PaddingValues(0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) { index ->
                    val pagerOffer = products[index]
                    OfferDetails(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                // Calculate the absolute offset for the current page from the
                                // scroll position. We use the absolute value which allows us to mirror
                                // any effects for both directions
                                val pageOffset = (
                                        (pagerState.currentPage - index) + pagerState
                                            .currentPageOffsetFraction
                                        ).absoluteValue

                                // We animate the scaleX + scaleY, between 85% and 100%
                                lerp(
                                    ScaleFactor(0.85f, 0.85f),
                                    ScaleFactor(1f, 1f),
                                    1f - pageOffset.coerceIn(0f, 1f)
                                ).also { scale ->
                                    scaleX = scale.scaleX
                                    scaleY = scale.scaleY
                                }

                                // We animate the alpha, between 50% and 100%
                                alpha = lerp(
                                    ScaleFactor(0.5f, 0.5f),
                                    ScaleFactor(1f, 1f),
                                    1f - pageOffset.coerceIn(0f, 1f)
                                ).scaleX
                            },
                        image = {
                            var isSuccess by remember {
                                mutableStateOf(true)
                            }
                            val imageNotFoundId = R.drawable.image_not_found

                            AsyncImage(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth()
                                    .height(250.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White)
                                ,
                                onError = {
                                    isSuccess = false
                                },
                                placeholder = painterResource(id = imageNotFoundId),
                                error = painterResource(id = imageNotFoundId),
                                model = pagerOffer.images[0],
                                contentScale = ContentScale.Fit,
                                contentDescription = null,
                            )
                        },
                        product = pagerOffer,
                        animationType = animationType,
                        onAddToCartButtonClick = {
                            onImageScreenEvent(ImageScreenEvent.OnAddToCart(offer.id))
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Товар добавлен в корзину", duration = SnackbarDuration.Short)
                            }
                        }
                    )
                    // val overZoomConfig = OverZoomConfig(
                    //     minSnapScale = 1f,
                    //     maxSnapScale = 1.7f
                    // )
                    // val zoomableState = rememberZoomableState(
                    //     initialScale = 1f,
                    //     minScale = 0.1f,
                    //     overZoomConfig = overZoomConfig,
                    // )
                    // var imageSize by remember {
                    //     mutableStateOf<Size?>(null)
                    // }
                    // LaunchedEffect(key1 = isTouching) {
                    //     val currentImageOffset = pagerState.currentPageOffsetFraction
                    //     if(currentImageOffset > 0.1f || currentImageOffset < -0.1f) {
                    //         coroutineScope.launch(Dispatchers.Main) {
                    //             zoomableState.animateScaleTo(1f)
                    //         }
                    //     }
                    // }
                    // LaunchedEffect(key1 = currentPage) {
                    //     if(imageSize != null && currentPage == index)
                    //         onImageScreenEvent(
                    //             ImageScreenEvent.OnPagerCurrentImageChange(
                    //                 imageSize!!
                    //             ))
                    // }
                    // LaunchedEffect(key1 = imageListSize) {
                    //     if(imageSize != null && currentPage == index)
                    //         onImageScreenEvent(
                    //             ImageScreenEvent.OnPagerCurrentImageChange(
                    //                 imageSize!!
                    //             ))
                    // }
                    // val zoomableStateScale = zoomableState.scale
                    // LaunchedEffect(key1 = zoomableStateScale) {
                    //     if(zoomableStateScale <= 0.5) {
                    //         onImageScreenEvent(ImageScreenEvent.OnCurrentScaleChange(zoomableStateScale))
                    //         if(!isTouching) {
                    //             onImageScreenEvent(ImageScreenEvent.OnBackToGallery)
                    //         }
                    //     }
                    // }
                    // Zoomable(
                    //     modifier = Modifier
                    //         .pointerInput(Unit) {
                    //             awaitEachGesture {
                    //                 awaitFirstDown(requireUnconsumed = false)
                    //                 do {
                    //                     val event = awaitPointerEvent()
                    //                     isTouching = true
                    //                     if (event.type == PointerEventType.Release) {
                    //                         isTouching = false
                    //                     }
                    //                 } while (event.changes.any { it.pressed })
                    //             }
                    //         }
//                             .graphicsLayer {
//                                 // Calculate the absolute offset for the current page from the
//                                 // scroll position. We use the absolute value which allows us to mirror
//                                 // any effects for both directions
//                                 val pageOffset = (
//                                         (pagerState.currentPage - index) + pagerState
//                                             .currentPageOffsetFraction
//                                         ).absoluteValue
//
//                                 // We animate the scaleX + scaleY, between 85% and 100%
//                                 lerp(
//                                     0.85f,
//                                     1f,
//                                     1f - pageOffset.coerceIn(0f, 1f)
//                                 ).also { scale ->
//                                     scaleX = scale
//                                     scaleY = scale
//                                 }
//
//                                 // We animate the alpha, between 50% and 100%
//                                 alpha = lerp(
//                                     0.5f,
//                                     1f,
//                                     1f - pageOffset.coerceIn(0f, 1f)
//                                 )
//                             },
                    //     state = zoomableState,
                    //     onTap = {
                    //         onImageScreenEvent(ImageScreenEvent.OnBarsVisibilityChange)
                    //     },
                    // ) {
                    //     val imageNotFoundId = remember {
                    //         R.drawable.image_not_found
                    //     }
                    //     AsyncImage(
                    //         modifier = Modifier
                    //             .then(
                    //                 if (imageSize != null) {
                    //                     Modifier.aspectRatio(
                    //                         imageSize!!.width / imageSize!!.height,
                    //                         isHorizontalOrientation
                    //                     )
                    //                 } else
                    //                     Modifier.fillMaxSize()
                    //             ),
                    //         error = painterResource(id = imageNotFoundId),
                    //         model = imagesList[index].urlImageList[0],
                    //         contentScale = ContentScale.Fit,
                    //         placeholder = painterResource(id = imageNotFoundId),
                    //         onError = { painterState ->
                    //             imageSize = painterState.painter?.intrinsicSize
                    //         },
                    //         onSuccess = { painterState ->
                    //             imageSize = painterState.painter.intrinsicSize
                    //         },
                    //         contentDescription = null,
                    //     )
                    // }
                }
            }
        }
    }
}