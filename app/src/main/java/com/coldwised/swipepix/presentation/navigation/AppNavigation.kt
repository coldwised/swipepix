package com.coldwised.swipepix.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.coldwised.swipepix.presentation.navigation.destination.*

@Composable
fun AppNavigation(
	navController: NavHostController
) {
	NavHost(
		navController = navController,
		startDestination = CATEGORIES_SCREEN_ROUTE
	) {
		products(
			onThemeSettingsClick = navController::navigateToThemeSettings
		)
		categories(
			onNavigateToThemeSettings = navController::navigateToThemeSettings,
			onNavigateToProducts = navController::navigateToProducts,
			onNavigateToCategories = navController::navigateToCategories,
		)
		themeSettings(
			onBackClick = navController::navigateUp
		)
		cart()
		favorites()
	}
}