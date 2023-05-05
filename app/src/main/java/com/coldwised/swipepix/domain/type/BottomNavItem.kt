package com.coldwised.swipepix.domain.type

import androidx.annotation.DrawableRes

data class BottomNavItem(
    val name: String,
    val route: String,
    val onClick: () -> Unit,
    @DrawableRes val iconId: Int,
    val badgeCount: Int = 0
)