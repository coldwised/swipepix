package com.coldwised.swipepix.presentation.gallery_screen.images_list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.coldwised.swipepix.presentation.gallery_screen.ImagesViewModel
import com.coldwised.swipepix.domain.type.Screen
import com.coldwised.swipepix.presentation.gallery_screen.full_screen.PagerScreen
import com.coldwised.swipepix.presentation.gallery_screen.images_list.components.ErrorLabel
import com.coldwised.swipepix.presentation.gallery_screen.images_list.components.GalleryScreenTopBar
import com.coldwised.swipepix.presentation.gallery_screen.images_list.components.LazyGridImages
import kotlinx.collections.immutable.toImmutableList

@Composable
fun GalleryScreen(
    navController: NavController,
    viewModel: ImagesViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    var savedPaddingValues by remember {
        mutableStateOf(PaddingValues(0.dp))
    }
    Scaffold(
        topBar = {
            GalleryScreenTopBar(
                onThemeSettingsClick = { navController.navigate(Screen.ThemeSettingsScreen.route) }
            )
        },
    ) { paddingValues ->
        savedPaddingValues = paddingValues
        Box(Modifier
            .padding(paddingValues)
            .fillMaxSize()
        ) {
            LazyGridImages(
                lazyGridState = state.lazyGridState,
                goodsList = state.goodsList.toImmutableList(),
                onGalleryScreenEvent = viewModel::onGalleryScreenEvent
            )
        }
    }
    if(state.error != null) {
        ErrorLabel(
            error = state.error,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.background)
            ,
            onRefreshClick = viewModel::onRefresh
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
    PagerScreen(
        paddingValues = savedPaddingValues,
        imagesList = state.goodsList,
        pagerScreenState = state.pagerScreenState,
        onImageScreenEvent = viewModel::onImageScreenEvent,
    )
}