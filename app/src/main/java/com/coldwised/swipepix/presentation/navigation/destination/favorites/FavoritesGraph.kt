package com.coldwised.swipepix.presentation.navigation.destination.favorites

import androidx.navigation.*
import com.coldwised.swipepix.presentation.navigation.destination.favorites.routes.FAVORITES_SCREEN_ROUTE
import com.coldwised.swipepix.presentation.navigation.destination.favorites.routes.favorites
import com.coldwised.swipepix.presentation.navigation.destination.navigateToThemeSettings

const val FAVORITES_GRAPH_NAME = "favorites_graph"

fun NavGraphBuilder.favoritesGraph(
	navController: NavHostController
) {
	navigation(
		startDestination = FAVORITES_SCREEN_ROUTE,
		route = FAVORITES_GRAPH_NAME
	) {
		favorites(
			onThemeSettingsClick = navController::navigateToThemeSettings,
		)
	}
}

fun NavController.navigateToFavoritesGraph() {
	val route = FAVORITES_GRAPH_NAME
	navigate(route) {
		popUpTo(route) {
			inclusive = true
		}
	}
}