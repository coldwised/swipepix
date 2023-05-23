package com.coldwised.swipepix.presentation.navigation.destination.favorites.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.coldwised.swipepix.presentation.favorites.FavoritesScreen

const val FAVORITES_SCREEN_ROUTE = "favorites"
fun NavGraphBuilder.favorites(
	onThemeSettingsClick: () -> Unit,
) {
	composable(
		route = FAVORITES_SCREEN_ROUTE,
	) {
		FavoritesScreen(
			onThemeSettingsClick = onThemeSettingsClick
		)
	}
}

fun NavController.navigateToFavorites() {
	navigate(FAVORITES_SCREEN_ROUTE)
}