package com.coldwised.swipepix.presentation.gallery.full_screen.state

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.IntOffset
import com.coldwised.swipepix.domain.type.ThemeStyleType

data class PagerScreenState(
    val isAnimatedScale: Boolean = false,
    val deleteDialogOpened: Boolean = false,
    val currentScale: Float = 1f,
    val animationState: AnimationState = AnimationState(),
    val pagerIndex: Int = 0,
    val isVisible: Boolean = false,
    val gridItemSize: Size = Size.Zero,
    val imageOffset: IntOffset = IntOffset.Zero,
    val painterIntrinsicSize: Size = Size.Zero,
    val topBarText: String = "",
    val systemNavigationBarVisible: Boolean = true,
    val topBarVisible: Boolean = false,
    val currentTheme: ThemeStyleType = ThemeStyleType.FollowAndroidSystem,
)