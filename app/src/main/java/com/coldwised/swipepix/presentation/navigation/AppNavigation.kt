package com.coldwised.swipepix.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.coldwised.swipepix.Constants
import com.coldwised.swipepix.domain.type.Screen
import com.coldwised.swipepix.presentation.cart.CartScreen
import com.coldwised.swipepix.presentation.catalog.categories.CategoriesScreen
import com.coldwised.swipepix.presentation.navigation.destination.gallery
import com.coldwised.swipepix.presentation.theme_settings.ThemeSettingsScreen

@Composable
fun AppNavigation(
	navController: NavHostController
) {
	val categoriesScreenRoute = remember { Screen.CategoriesScreen.route }
	NavHost(
		navController = navController,
		startDestination = categoriesScreenRoute
	) {
		gallery(onThemeSettingsClick = )
		composable(
			route = Screen.ThemeSettingsScreen.route
		) {
			ThemeSettingsScreen(
				navController = navHostController
			)
		}
		composable(
			route = Screen.CartScreen.route
		) {
			CartScreen(
				navController = navHostController
			)
		}
		composable(
			route = Screen.FavoritesScreen.route
		) {
			CartScreen(
				navController = navHostController
			)
		}
		composable(
			route = categoriesScreenRoute,
			arguments = listOf(navArgument(Constants.PARENT_CATEGORY_ID_PARAM) { nullable = true })
		) {
			CategoriesScreen(
				navController = navHostController
			)
		}
		composable(
			route = Screen.ProfileScreen.route
		) {
			CartScreen(
				navController = navHostController
			)
		}
	}
}