package com.coldwised.swipepix.presentation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.coldwised.swipepix.domain.type.BottomNavItem

@Composable
fun BottomNavigationBar(
	items: List<BottomNavItem>,
	navController: NavController,
	modifier: Modifier = Modifier,
) {
	val backStackEntry = navController.currentBackStackEntryAsState().value
	val routeName = backStackEntry?.destination?.route?.substringBefore('/') ?: return
	NavigationBar(
		modifier = modifier
			.navigationBarsPadding()
			//.height(78.dp)
			.shadow(4.dp),
		//windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp),
	) {
		items.forEach { item ->
			val selected = item.screen.route == backStackEntry.destination.route
				|| item.screen.subRoutes?.contains(
				routeName) ?: false
			NavigationBarItem(
				//interactionSource = NoRippleInteractionSource(),
				selected = selected,
				onClick = {
					if(backStackEntry.destination.route != item.screen.route) {
						navController.navigate(item.screen.route) {
							popUpTo(item.screen.route) {
								inclusive = true
							}
						}
					}
				},
				icon = {
					Icon(
						painter = painterResource(id = item.iconId),
						contentDescription = null,
					)
				},
				label = {
					Text(
						text = item.screen.screenName.asString(),
						fontSize = 13.sp,
					)
				}
			)
		}
	}
}