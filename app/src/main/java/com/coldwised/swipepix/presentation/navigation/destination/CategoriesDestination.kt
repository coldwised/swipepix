package com.coldwised.swipepix.presentation.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.coldwised.swipepix.presentation.catalog.categories.CategoriesScreen

fun NavGraphBuilder.categories(
	onThemeSettingsClick: () -> Unit
) {
	composable(
		route = "Categories?id={id}",
		arguments = listOf(navArgument("id") { nullable = true; type = NavType.StringType })
	) {
		CategoriesScreen(
			onCategoryClick = {
				if(it.childCategories.isEmpty()) {
					nav
				} else {
					nav
				}
			}
		)
	}
}