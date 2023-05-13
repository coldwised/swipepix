package com.coldwised.swipepix.presentation.navigation.destination

import androidx.navigation.NavController
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
	val route = PROFILE_SCREEN_ROUTE
	navigate(route) {
		val backQueue = backQueue
		for(i in backQueue.indices) {
			if(backQueue[i].destination.route == route) {
				val entriesToDelete = backQueue.subList(i, backQueue.size)
				val savedEntries = entriesToDelete.drop(2)
				backQueue.removeAll(entriesToDelete)
				backQueue.addAll(savedEntries)
				break
			}
		}
	}
}