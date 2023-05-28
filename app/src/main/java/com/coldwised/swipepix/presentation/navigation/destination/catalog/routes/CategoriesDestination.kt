package com.coldwised.swipepix.presentation.navigation.destination.catalog.routes

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.coldwised.swipepix.data.remote.dto.CategoryDto
import com.coldwised.swipepix.presentation.catalog.categories.CategoriesScreen

private const val BASE_ROUTE = "categories"
private const val ID_KEY = "id"
private const val CATEGORY_NAME_KEY = "category_name"
const val CATEGORIES_SCREEN_ROUTE = "$BASE_ROUTE?$ID_KEY={$ID_KEY}?$CATEGORY_NAME_KEY={$CATEGORY_NAME_KEY}"

fun NavGraphBuilder.categories(
	onNavigateToThemeSettings: () -> Unit,
	onNavigateToProducts: (CategoryDto) -> Unit,
	onNavigateToCategories: (CategoryDto) -> Unit,
	onNavigateBack: () -> Unit,
) {
	composable(
		route = CATEGORIES_SCREEN_ROUTE,
		arguments = listOf(
			navArgument(ID_KEY) { nullable = true; type = NavType.StringType },
			navArgument(CATEGORY_NAME_KEY) { nullable = true; type = NavType.StringType },
		)
	) { navBackStackEntry ->
		val arguments = navBackStackEntry.arguments
		val encodedId = arguments?.getString(ID_KEY)
		val encodedCategoryName = arguments?.getString(CATEGORY_NAME_KEY)
		val categoryName = Uri.decode(encodedCategoryName)
		val id = Uri.decode(encodedId)
		CategoriesScreen(
			categoryId = id,
			categoryName = categoryName,
			onCategoryClick = {
				if(it.childCategories.isEmpty()) {
					onNavigateToProducts(it)
				} else {
					onNavigateToCategories(it)
				}
			},
			onThemeSettingsClick = onNavigateToThemeSettings,
			onBackClick = onNavigateBack
		)
	}
}

fun NavController.navigateToCategories(category: CategoryDto) {
	val encodedId: String? = Uri.encode(category.id)
	val encodedCategoryName: String? = Uri.encode(category.name)
	navigate("$BASE_ROUTE?$ID_KEY=$encodedId?$CATEGORY_NAME_KEY=$encodedCategoryName")
}