package com.coldwised.swipepix.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.coldwised.swipepix.presentation.navigation.destination.*
import com.coldwised.swipepix.presentation.navigation.destination.cart.cartGraph
import com.coldwised.swipepix.presentation.navigation.destination.catalog.CATALOG_GRAPH_NAME
import com.coldwised.swipepix.presentation.navigation.destination.catalog.catalogGraph
import com.coldwised.swipepix.presentation.navigation.destination.favorites.favoritesGraph

@Composable
fun AppNavigation(
	navController: NavHostController
) {
	NavHost(
		navController = navController,
		startDestination = CATALOG_GRAPH_NAME
	) {
		catalogGraph(navController)
		favoritesGraph(navController)
		cartGraph(navController)
		themeSettings(
			onBackClick = navController::navigateUp
		)
		profile()
	}
}