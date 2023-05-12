package com.coldwised.swipepix.presentation.navigation.destination.catalog

import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
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
	navigate(CATEGORIES_SCREEN_ROUTE) {
		val existingNode = graph.findNode(CATEGORIES_SCREEN_ROUTE)
		existingNode?.let {
			graph.remove(existingNode)
		}
		launchSingleTop = true
		// currentDestination.parent.rem
		// val isExisting = currentDestination?.parent?.hierarchy?.any {
		// 	it.route == CATEGORIES_SCREEN_ROUTE
		// }
		// launchSingleTop = true
		// val asdf = graph.findStartDestination()
		// popUpTo(graph.findStartDestination().id) {
		// 	this.
		// 	inclusive = false
		// }
		// popUpTo(graph.findStartDestination().id) {
		// 	//inclusive = true
		// }
		//launchSingleTop = true
	}
}