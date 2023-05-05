package com.coldwised.swipepix.presentation.navigation.destination

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.coldwised.swipepix.presentation.cart.CartScreen

private const val BASE_ROUTE = "cart"
fun NavGraphBuilder.cart(
) {
	composable(
		route = BASE_ROUTE,
	) {
		CartScreen()
	}
}

fun NavController.navigateToCart() {
	navigate(BASE_ROUTE)
}