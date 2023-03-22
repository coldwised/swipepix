package com.coldwised.swipepix.domain.model

import com.coldwised.swipepix.domain.type.ThemeStyleType


data class AppConfiguration(
    val useDynamicColors: Boolean,
    val themeStyle: ThemeStyleType,
    val usePowerSavingMode: Boolean,
)