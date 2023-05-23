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