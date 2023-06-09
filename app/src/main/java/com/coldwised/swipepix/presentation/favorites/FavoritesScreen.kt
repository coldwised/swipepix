package com.coldwised.swipepix.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldwised.swipepix.R
import com.coldwised.swipepix.data.remote.dto.ProductDto
import com.coldwised.swipepix.presentation.catalog.full_screen.PagerScreen
import com.coldwised.swipepix.presentation.catalog.full_screen.event.ImageScreenEvent
import com.coldwised.swipepix.presentation.catalog.full_screen.state.PagerScreenState
import com.coldwised.swipepix.presentation.catalog.images_list.components.ErrorLabel
import com.coldwised.swipepix.presentation.catalog.images_list.components.LazyGridImages
import com.coldwised.swipepix.presentation.catalog.images_list.event.GalleryScreenEvent
import com.coldwised.swipepix.presentation.favorites.components.FavoriteTopBar
import com.coldwised.swipepix.util.UiText

@Composable
internal fun FavoritesScreen(
    onThemeSettingsClick: () -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = true) {
        viewModel.onStart()
    }
    val state = viewModel.state.collectAsStateWithLifecycle().value
    GalleryScreen(
        products = state.goodsList,
        pagerScreenState = state.pagerScreenState,
        isLoading  = state.isLoading,
        error = state.error,
        lazyGridState = state.lazyGridState,
        onImageScreenEvent = viewModel::onImageScreenEvent,
        onRefreshClick = viewModel::onStart,
        onThemeSettingsClick = onThemeSettingsClick,
        onGalleryScreenEvent = viewModel::onGalleryScreenEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GalleryScreen(
    products: List<ProductDto>?,
    pagerScreenState: PagerScreenState,
    isLoading: Boolean,
    error: UiText?,
    lazyGridState: LazyGridState,
    onImageScreenEvent: (ImageScreenEvent) -> Unit,
    onRefreshClick: () -> Unit,
    onThemeSettingsClick: () -> Unit,
    onGalleryScreenEvent: (GalleryScreenEvent) -> Unit,
) {
    var savedPaddingValues by remember {
        mutableStateOf(PaddingValues(0.dp))
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            FavoriteTopBar(
                onThemeSettingsClick = onThemeSettingsClick,
                scrollBehavior = scrollBehavior
            )
        },
    ) { paddingValues ->
        savedPaddingValues = paddingValues
        Box(
            Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .fillMaxSize()
        ) {
            products?.takeIf { it.isEmpty() }?.let {
                Text(
                    text = stringResource(id = R.string.products_not_found),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
            LazyGridImages(
                lazyGridState = lazyGridState,
                goodsList = products.orEmpty(),
                onGalleryScreenEvent = onGalleryScreenEvent
            )
        }
        if(error != null) {
            ErrorLabel(
                error = error,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .background(MaterialTheme.colorScheme.background)
                ,
                onRefreshClick = onRefreshClick
            )
        }
        if(isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
    PagerScreen(
        paddingValues = savedPaddingValues,
        products = products.orEmpty(),
        pagerScreenState = pagerScreenState,
        onImageScreenEvent = onImageScreenEvent,
    )
}