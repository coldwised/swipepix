package com.coldwised.swipepix.presentation.gallery_screen.full_screen.event

import androidx.compose.ui.geometry.Size
import com.coldwised.swipepix.presentation.gallery_screen.full_screen.type.AnimationType
sealed interface ImageScreenEvent {
    data class OnAnimate(val value: AnimationType): ImageScreenEvent
    data class OnCurrentScaleChange(val scale: Float): ImageScreenEvent
    data class OnVisibleChanged(val value: Boolean): ImageScreenEvent
    data class OnPagerIndexChanged(val value: Int): ImageScreenEvent
    data class OnPagerCurrentImageChange(val painterIntrinsicSize: Size): ImageScreenEvent
    object OnBackToGallery: ImageScreenEvent
    object OnBarsVisibilityChange: ImageScreenEvent
}