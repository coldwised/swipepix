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
private const val SEARCH_QUERY_KEY = "search_query"
private const val TOP_BAR_TITLE_KEY = "top_bar_title"
private const val BASE_ROUTE = "products"
fun NavGraphBuilder.products(
	onNavigateBack: () -> Unit,
) {
	composable(
		route = "$BASE_ROUTE/{$TOP_BAR_TITLE_KEY}?$ID_KEY={$ID_KEY}?$SEARCH_QUERY_KEY={$SEARCH_QUERY_KEY}",
		arguments = listOf(
			navArgument(TOP_BAR_TITLE_KEY) { type = NavType.StringType; nullable = false },
			navArgument(ID_KEY) { type = NavType.StringType; nullable = true },
			navArgument(SEARCH_QUERY_KEY) { type = NavType.StringType; nullable = true },
		)
	) { navBackStackEntry ->
		val arguments = navBackStackEntry.arguments
		val searchQuery: String? = Uri.decode(arguments?.getString(SEARCH_QUERY_KEY))
		val categoryId: String? = Uri.decode(arguments?.getString(ID_KEY))
		val topBarTitle = Uri.decode(arguments?.getString(TOP_BAR_TITLE_KEY))
		GalleryScreen(
			topBarTitle = topBarTitle,
			onBackClick = onNavigateBack,
			categoryId = categoryId,
			searchQuery = searchQuery
		)
	}
}

fun NavController.navigateToProducts(category: CategoryDto) {
	val encodedId: String? = Uri.encode(category.id)
	val encodedCategoryName: String? = Uri.encode(category.name)
	navigate("$BASE_ROUTE/$encodedCategoryName?$ID_KEY=$encodedId")
}

fun NavController.navigateToProducts(searchQuery: String) {
	val encodedSearchQuery: String = Uri.encode(searchQuery)
	navigate("$BASE_ROUTE/$encodedSearchQuery?$SEARCH_QUERY_KEY=$encodedSearchQuery")
}