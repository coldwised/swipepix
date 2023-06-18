package com.coldwised.swipepix.presentation.navigation.destination.catalog

import androidx.navigation.*
import com.coldwised.swipepix.presentation.navigation.destination.catalog.routes.CATEGORIES_SCREEN_ROUTE
import com.coldwised.swipepix.presentation.navigation.destination.catalog.routes.categories
import com.coldwised.swipepix.presentation.navigation.destination.catalog.routes.navigateToCategories
import com.coldwised.swipepix.presentation.navigation.destination.catalog.routes.navigateToProducts
import com.coldwised.swipepix.presentation.navigation.destination.catalog.routes.products

const val CATALOG_GRAPH_NAME = "catalog"

fun NavGraphBuilder.catalogGraph(
	navController: NavHostController
) {
	navigation(
		startDestination = CATEGORIES_SCREEN_ROUTE,
		route = CATALOG_GRAPH_NAME
	) {
		categories(
			onNavigateToProducts = navController::navigateToProducts,
			onNavigateToProductsWithQuery = navController::navigateToProducts,
			onNavigateToCategories = navController::navigateToCategories,
			onNavigateBack = navController::navigateUp,
		)
		products(
			onNavigateBack = navController::navigateUp,
			onNavigateToProductsWithQuery = navController::navigateToProducts
		)
	}
}
fun NavController.navigateToCatalogGraph() {
	val route = CATALOG_GRAPH_NAME
	navigate(route) {
		popUpTo(route) {
			inclusive = true
		}
	}
}