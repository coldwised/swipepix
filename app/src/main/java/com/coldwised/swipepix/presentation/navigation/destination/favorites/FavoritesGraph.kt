package com.coldwised.swipepix.presentation.navigation.destination.favorites

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.coldwised.swipepix.presentation.navigation.destination.cart.CART_GRAPH_NAME
import com.coldwised.swipepix.presentation.navigation.destination.cart.routes.CART_PREVIEW_ROUTE
import com.coldwised.swipepix.presentation.navigation.destination.catalog.routes.CATEGORIES_SCREEN_ROUTE
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
	navigate(FAVORITES_SCREEN_ROUTE) {
		// popUpTo(graph.findStartDestination().id) {
		// 	pop
		// }
		launchSingleTop = true
		//restoreState = true
	}
}