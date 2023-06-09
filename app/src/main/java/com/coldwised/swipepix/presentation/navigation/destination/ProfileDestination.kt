package com.coldwised.swipepix.presentation.navigation.destination

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.coldwised.swipepix.presentation.profile.ProfileScreen

const val PROFILE_SCREEN_ROUTE = "profile"

fun NavGraphBuilder.profile(
	onNavigateToThemeSettings: () -> Unit
) {
	composable(
		route = PROFILE_SCREEN_ROUTE,
	) {
		ProfileScreen(
			onNavigateToThemeSettings = onNavigateToThemeSettings
		)
	}
}

fun NavController.navigateToProfile() {
	val route = PROFILE_SCREEN_ROUTE
	navigate(route) {
		popUpTo(route) {
			inclusive = true
		}
	}
}