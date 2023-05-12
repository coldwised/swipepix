package com.coldwised.swipepix.presentation.navigation.destination

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.coldwised.swipepix.presentation.profile.ProfileScreen

const val PROFILE_SCREEN_ROUTE = "profile"

fun NavGraphBuilder.profile() {
	composable(
		route = PROFILE_SCREEN_ROUTE,
	) {
		ProfileScreen()
	}
}

fun NavController.navigateToProfile() {
	navigate(PROFILE_SCREEN_ROUTE) {
		popUpTo(graph.findStartDestination().id)
		launchSingleTop = true
	}
}