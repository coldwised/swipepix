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
	val route = CATALOG_GRAPH_NAME
	navigate(route) {
		val backQueue = backQueue
		for(i in backQueue.indices) {
			if(backQueue[i].destination.route == route) {
				val entriesToDelete = backQueue.subList(i, backQueue.size)
				val savedEntries = entriesToDelete.drop(2)
				backQueue.removeAll(entriesToDelete)
				backQueue.addAll(savedEntries)
				break
			}
		}
	}
}