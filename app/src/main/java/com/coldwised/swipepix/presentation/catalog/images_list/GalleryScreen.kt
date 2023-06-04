package com.coldwised.swipepix.presentation.catalog.images_list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldwised.swipepix.data.remote.dto.ProductDto
import com.coldwised.swipepix.presentation.catalog.ImagesViewModel
import com.coldwised.swipepix.presentation.catalog.full_screen.PagerScreen
import com.coldwised.swipepix.presentation.catalog.full_screen.event.ImageScreenEvent
import com.coldwised.swipepix.presentation.catalog.full_screen.state.PagerScreenState
import com.coldwised.swipepix.presentation.catalog.images_list.components.ErrorLabel
import com.coldwised.swipepix.presentation.catalog.images_list.components.GalleryScreenTopBar
import com.coldwised.swipepix.presentation.catalog.images_list.components.LazyGridImages
import com.coldwised.swipepix.presentation.catalog.images_list.event.GalleryScreenEvent
import com.coldwised.swipepix.presentation.component.FoundProducts
import com.coldwised.swipepix.util.UiText

@Composable
internal fun GalleryScreen(
    categoryId: String?,
    searchQuery: String?,
    topBarTitle: String,
    onBackClick: () -> Unit,
    onSearchClick: (String) -> Unit,
    viewModel: ImagesViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = true) {
        viewModel.onStart(categoryId, searchQuery)
    }
    val state = viewModel.state.collectAsStateWithLifecycle().value
    GalleryScreen(
        products = state.goodsList,
        pagerScreenState = state.pagerScreenState,
        isLoading  = state.isLoading,
        topBarTitle = topBarTitle,
        error = state.error,
        lazyGridState = state.lazyGridState,
        searchQuery = state.searchQuery,
        foundProducts = state.foundProducts,
        onImageScreenEvent = viewModel::onImageScreenEvent,
        onRefreshClick = { viewModel.onStart(categoryId, searchQuery) },
        onBackClick = onBackClick,
        onGalleryScreenEvent = viewModel::onGalleryScreenEvent,
        onSearchHide = viewModel::onSearchHide,
        onSearchShow = viewModel::onSearchShow,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onSearchClick = onSearchClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GalleryScreen(
    topBarTitle: String,
    products: List<ProductDto>?,
    pagerScreenState: PagerScreenState,
    isLoading: Boolean,
    error: UiText?,
    lazyGridState: LazyGridState,
    foundProducts: List<ProductDto>?,
    onImageScreenEvent: (ImageScreenEvent) -> Unit,
    onRefreshClick: () -> Unit,
    onBackClick: () -> Unit,
    onGalleryScreenEvent: (GalleryScreenEvent) -> Unit,
    searchQuery: String,
    onSearchHide: () -> Unit,
    onSearchShow: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onSearchClick: (String) -> Unit,
) {
    var savedPaddingValues by remember {
        mutableStateOf(PaddingValues(0.dp))
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            GalleryScreenTopBar(
                searchQuery = searchQuery,
                title = topBarTitle,
                scrollBehavior = scrollBehavior,
                onSearchQueryChanged = onSearchQueryChanged,
                onBackClick = onBackClick,
                onSearchClick = onSearchClick,
                onSearchShow = onSearchShow,
                onSearchHide = onSearchHide,
            )
        },
    ) { paddingValues ->
        savedPaddingValues = paddingValues
        Box(
            Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .fillMaxSize()
        ) {
            if(isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if(error != null) {
                ErrorLabel(
                    error = error,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .background(MaterialTheme.colorScheme.background)
                    ,
                    onRefreshClick = onRefreshClick
                )
            } else if(foundProducts != null) {
                FoundProducts(
                    products = foundProducts,
                    onItemClick = onSearchClick,
                )
            } else if(products != null) {
                if(products.isEmpty()) {
                    Text(
                        text = "Нет доступных товаров",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                } else {
                    LazyGridImages(
                        lazyGridState = lazyGridState,
                        goodsList = products,
                        onGalleryScreenEvent = onGalleryScreenEvent
                    )
                }
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