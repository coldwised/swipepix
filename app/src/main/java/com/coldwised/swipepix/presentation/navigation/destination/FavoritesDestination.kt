package com.coldwised.swipepix.presentation.navigation.destination

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.coldwised.swipepix.presentation.favorites.FavoritesScreen

private const val BASE_ROUTE = "favorites"
fun NavGraphBuilder.favorites(
) {
	composable(
		route = BASE_ROUTE,
	) {
		FavoritesScreen()
	}
}

fun NavController.navigateToFavorites() {
	navigate(BASE_ROUTE)
}