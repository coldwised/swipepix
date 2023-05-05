package com.coldwised.swipepix.presentation.navigation.destination.favorites

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.coldwised.swipepix.presentation.navigation.destination.favorites.routes.FAVORITES_SCREEN_ROUTE
import com.coldwised.swipepix.presentation.navigation.destination.favorites.routes.favorites

const val FAVORITES_GRAPH_NAME = "favorites_graph"

fun NavGraphBuilder.favoritesGraph(
	navController: NavHostController
) {
	navigation(
		startDestination = FAVORITES_SCREEN_ROUTE,
		route = FAVORITES_GRAPH_NAME
	) {
		favorites()
	}
}

fun NavController.navigateToFavoritesGraph() {
	navigate(FAVORITES_GRAPH_NAME)
}