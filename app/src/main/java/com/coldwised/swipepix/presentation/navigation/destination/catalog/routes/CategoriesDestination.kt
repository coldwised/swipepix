package com.coldwised.swipepix.presentation.navigation.destination.catalog.routes

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.coldwised.swipepix.presentation.catalog.categories.CategoriesScreen

private const val BASE_ROUTE = "categories"
private const val ID_KEY = "id"
const val CATEGORIES_SCREEN_ROUTE = "$BASE_ROUTE?$ID_KEY={$ID_KEY}"

fun NavGraphBuilder.categories(
	onNavigateToThemeSettings: () -> Unit,
	onNavigateToProducts: (String) -> Unit,
	onNavigateToCategories: (String) -> Unit,
	onNavigateBack: () -> Unit,
) {
	composable(
		route = "$BASE_ROUTE?$ID_KEY={$ID_KEY}",
		arguments = listOf(navArgument(ID_KEY) { nullable = true; type = NavType.StringType })
	) { navBackStackEntry ->
		val arguments = navBackStackEntry.arguments
		val encodedId = arguments?.getString(ID_KEY)
		val id = Uri.decode(encodedId)
		CategoriesScreen(
			categoryId = id,
			onCategoryClick = {
				if(it.childCategories.isEmpty()) {
					onNavigateToProducts(it.id)
				} else {
					onNavigateToCategories(it.id)
				}
			},
			onThemeSettingsClick = onNavigateToThemeSettings,
			onBackClick = onNavigateBack
		)
	}
}

fun NavController.navigateToCategories(id: String?) {
	val encodedId: String? = Uri.encode(id)
	navigate("$BASE_ROUTE?$ID_KEY=$encodedId")
}