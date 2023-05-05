package com.coldwised.swipepix.presentation.navigation.destination.catalog

import androidx.navigation.*
import com.coldwised.swipepix.presentation.navigation.destination.catalog.routes.*
import com.coldwised.swipepix.presentation.navigation.destination.navigateToThemeSettings

const val CATALOG_GRAPH_NAME = "catalog"

fun NavGraphBuilder.catalogGraph(
	navController: NavHostController
) {
	navigation(
		startDestination = CATEGORIES_SCREEN_ROUTE,
		route = CATALOG_GRAPH_NAME
	) {
		categories(
			onNavigateToThemeSettings = navController::navigateToThemeSettings,
			onNavigateToProducts = navController::navigateToProducts,
			onNavigateToCategories = navController::navigateToCategories,
		)
		products(
			onThemeSettingsClick = navController::navigateToThemeSettings
		)
	}
}
fun NavController.navigateToCatalogGraph() {
	navigate(CATALOG_GRAPH_NAME)
}