package com.coldwised.swipepix.presentation.catalog.full_screen.state

import android.os.Parcelable
import com.coldwised.swipepix.presentation.catalog.full_screen.type.AnimationType
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimationState(
    val animationType: AnimationType = AnimationType.HIDE_ANIMATION,
    val isAnimationInProgress: Boolean = true,
): Parcelable