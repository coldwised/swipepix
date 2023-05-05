package com.coldwised.swipepix.presentation.navigation.destination

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.coldwised.swipepix.presentation.theme_settings.ThemeSettingsScreen

private const val BASE_ROUTE = "theme_settings"
fun NavGraphBuilder.themeSettings(
	onBackClick: () -> Unit
) {
	composable(
		route = BASE_ROUTE,
	) {
		ThemeSettingsScreen(
			onBackClick = onBackClick
		)
	}
}

fun NavController.navigateToThemeSettings() {
	navigate(BASE_ROUTE)
}