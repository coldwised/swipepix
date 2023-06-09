package com.coldwised.swipepix.presentation.catalog.images_list.event

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.IntOffset

@Stable
sealed interface GalleryScreenEvent {
    data class OnSavePainterIntrinsicSize(val size: Size): GalleryScreenEvent
    data class OnSaveGridItemOffsetToScroll(val yOffset: Int): GalleryScreenEvent
    data class OnSaveCurrentGridItemOffset(val intOffset: IntOffset): GalleryScreenEvent
    data class OnCartClick(val productId: String): GalleryScreenEvent
    data class OnImageClick(val index: Int): GalleryScreenEvent
}