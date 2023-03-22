package com.coldwised.swipepix.domain.use_case

import com.coldwised.swipepix.domain.datastore.UserPreferences
import com.coldwised.swipepix.domain.model.AppConfiguration
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppConfigurationStreamUseCase @Inject constructor(
    private val userPreferences: UserPreferences
) {
    operator fun invoke(): Flow<AppConfiguration> =
        userPreferences.appConfigurationStream
}