package com.coldwised.swipepix.presentation.navigation.destination.catalog.routes

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.coldwised.swipepix.presentation.catalog.images_list.GalleryScreen

private const val ID_KEY = "id"
private const val BASE_ROUTE = "products"
fun NavGraphBuilder.products(
	onThemeSettingsClick: () -> Unit
) {
	composable(
		route = "$BASE_ROUTE/$ID_KEY",
		arguments = listOf(navArgument(ID_KEY) { type = NavType.StringType; nullable = false })
	) { navBackStackEntry ->
		val arguments = navBackStackEntry.arguments
		val encodedId = arguments?.getString(ID_KEY)
		val id = Uri.decode(encodedId)
		GalleryScreen(
			categoryId = id,
			onThemeSettingsClick = onThemeSettingsClick
		)
	}
}

fun NavController.navigateToProducts(id: String) {
	val encodedId: String? = Uri.encode(id)
	navigate("$BASE_ROUTE/$encodedId")
}