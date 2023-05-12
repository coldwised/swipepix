package com.coldwised.swipepix.presentation.navigation.destination.cart

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.coldwised.swipepix.presentation.navigation.destination.cart.routes.CART_PREVIEW_ROUTE
import com.coldwised.swipepix.presentation.navigation.destination.cart.routes.cart
import com.coldwised.swipepix.presentation.navigation.destination.favorites.routes.FAVORITES_SCREEN_ROUTE

const val CART_GRAPH_NAME = "cart"

fun NavGraphBuilder.cartGraph(
	navController: NavHostController
) {
	navigation(
		startDestination = CART_PREVIEW_ROUTE,
		route = CART_GRAPH_NAME
	) {
		cart()
	}
}

fun NavController.navigateToCartGraph() {
	navigate(CART_PREVIEW_ROUTE) {
		// popUpTo(CART_PREVIEW_ROUTE) {
		// 	//inclusive = true
		// 	//saveState = true
		// }
		//popUpTo(graph.findStartDestination().id)
		launchSingleTop = true
		//restoreState = true
	}
}

