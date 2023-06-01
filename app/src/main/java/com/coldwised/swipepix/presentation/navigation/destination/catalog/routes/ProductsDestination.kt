package com.coldwised.swipepix.presentation.navigation.destination.catalog.routes

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.coldwised.swipepix.data.remote.dto.CategoryDto
import com.coldwised.swipepix.presentation.catalog.images_list.GalleryScreen

private const val ID_KEY = "id"
private const val CATEGORY_NAME_KEY = "category_name"
private const val BASE_ROUTE = "products"
fun NavGraphBuilder.products(
	onNavigateBack: () -> Unit,
) {
	composable(
		route = "$BASE_ROUTE/{$ID_KEY}/{$CATEGORY_NAME_KEY}",
		arguments = listOf(
			navArgument(ID_KEY) { type = NavType.StringType; nullable = false },
			navArgument(CATEGORY_NAME_KEY) { type = NavType.StringType; nullable = false },
		)
	) { navBackStackEntry ->
		val arguments = navBackStackEntry.arguments
		val encodedId = arguments?.getString(ID_KEY)
		val encodedCategoryName = arguments?.getString(CATEGORY_NAME_KEY)
		val id = Uri.decode(encodedId)
		val categoryName = Uri.decode(encodedCategoryName)
		GalleryScreen(
			topBarTitle = categoryName,
			onBackClick = onNavigateBack,
			categoryId = id,
		)
	}
}

fun NavController.navigateToProducts(category: CategoryDto) {
	val encodedId: String? = Uri.encode(category.id)
	val encodedCategoryName: String? = Uri.encode(category.name)
	navigate("$BASE_ROUTE/$encodedId/$encodedCategoryName")
}