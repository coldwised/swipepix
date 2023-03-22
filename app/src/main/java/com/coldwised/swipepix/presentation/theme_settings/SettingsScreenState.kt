package com.coldwised.swipepix.presentation.theme_settings

import com.coldwised.swipepix.domain.type.ThemeStyleType

data class ThemeSettingsScreenState(
    val useDynamicColors: Boolean,
    val themeStyle: ThemeStyleType
)