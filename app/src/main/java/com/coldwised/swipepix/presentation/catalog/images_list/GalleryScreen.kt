package com.coldwised.swipepix.presentation.catalog.images_list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.coldwised.swipepix.presentation.catalog.ImagesViewModel
import com.coldwised.swipepix.presentation.catalog.full_screen.PagerScreen
import com.coldwised.swipepix.presentation.catalog.images_list.components.ErrorLabel
import com.coldwised.swipepix.presentation.catalog.images_list.components.GalleryScreenTopBar
import com.coldwised.swipepix.presentation.catalog.images_list.components.LazyGridImages

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    categoryId: String,
    onThemeSettingsClick: () -> Unit,
    viewModel: ImagesViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = true) {
        viewModel.onStart(categoryId)
    }
    val state = viewModel.state.collectAsState().value
    var savedPaddingValues by remember {
        mutableStateOf(PaddingValues(0.dp))
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            GalleryScreenTopBar(
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
            LazyGridImages(
                lazyGridState = state.lazyGridState,
                goodsList = state.goodsList,
                onGalleryScreenEvent = viewModel::onGalleryScreenEvent
            )
        }
        if(state.error != null) {
            ErrorLabel(
                error = state.error,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .background(MaterialTheme.colorScheme.background)
                ,
                onRefreshClick = { viewModel.onStart(categoryId) }
            )
        }
        if(state.isLoading) {
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
        products = state.goodsList,
        pagerScreenState = state.pagerScreenState,
        onImageScreenEvent = viewModel::onImageScreenEvent,
        scrollBehavior = scrollBehavior
    )
}