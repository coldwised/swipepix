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
	val route = CART_GRAPH_NAME
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

