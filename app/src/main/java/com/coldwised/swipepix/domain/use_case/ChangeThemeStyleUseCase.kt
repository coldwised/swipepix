package com.coldwised.swipepix.domain.use_case

import com.coldwised.swipepix.domain.datastore.UserPreferences
import com.coldwised.swipepix.domain.type.ThemeStyleType
import javax.inject.Inject
class ChangeThemeStyleUseCase @Inject constructor(
    private val userPreferences: UserPreferences
) {
    suspend operator fun invoke(themeStyle: ThemeStyleType) =
        userPreferences.changeThemeStyle(themeStyle = themeStyle)
}