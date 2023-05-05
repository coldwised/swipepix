package com.coldwised.swipepix.presentation.navigation.destination.cart.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.coldwised.swipepix.presentation.cart.CartScreen

const val CART_PREVIEW_ROUTE = "cart_preview"
fun NavGraphBuilder.cart(
) {
	composable(
		route = CART_PREVIEW_ROUTE,
	) {
		CartScreen()
	}
}

fun NavController.navigateToCart() {
	navigate(CART_PREVIEW_ROUTE)
}