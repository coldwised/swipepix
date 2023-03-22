package com.coldwised.swipepix.domain.datastore

import com.coldwised.swipepix.domain.model.AppConfiguration
import com.coldwised.swipepix.domain.type.ThemeStyleType
import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    val appConfigurationStream: Flow<AppConfiguration>

    suspend fun toggleDynamicColors()

    suspend fun changeThemeStyle(themeStyle: ThemeStyleType)
}