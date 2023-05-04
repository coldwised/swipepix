package com.coldwised.swipepix.presentation.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.coldwised.swipepix.Constants
import com.coldwised.swipepix.domain.type.Screen
import com.coldwised.swipepix.presentation.catalog.images_list.GalleryScreen

fun NavGraphBuilder.gallery(
	onThemeSettingsClick: () -> Unit
) {
	composable(
		route = Screen.ProductsScreen.route,
		arguments = listOf(navArgument(Constants.PARENT_CATEGORY_ID_PARAM) { type = NavType.StringType })
	) {
		GalleryScreen(
			onThemeSettingsClick = onThemeSettingsClick
		)
	}
}