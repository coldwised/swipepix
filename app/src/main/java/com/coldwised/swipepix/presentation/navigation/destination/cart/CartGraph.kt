package com.coldwised.swipepix.presentation.navigation.destination.cart

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.coldwised.swipepix.presentation.navigation.destination.cart.routes.CART_PREVIEW_ROUTE
import com.coldwised.swipepix.presentation.navigation.destination.cart.routes.cart

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
	navigate(CART_GRAPH_NAME)
}

