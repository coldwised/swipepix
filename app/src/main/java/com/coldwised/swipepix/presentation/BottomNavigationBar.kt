package com.coldwised.swipepix.presentation

import androidx.compose.material3.*
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
	val route = backStackEntry?.destination?.route ?: return
	NavigationBar(
		modifier = modifier
			//.navigationBarsPadding()
			//.height(78.dp)
			.shadow(4.dp),
		//windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp),
	) {
		val colorScheme = MaterialTheme.colorScheme
		items.forEach { item ->
			val selected = route.startsWith(item.route)
			NavigationBarItem(
				//interactionSource = NoRippleInteractionSource(),
				selected = selected,
				onClick = item.onClick,
				colors = NavigationBarItemDefaults.colors(
					selectedTextColor = colorScheme.primary,
					selectedIconColor = colorScheme.primary,
					//indicatorColor = Color.Transparent
				),
				icon = {
					Icon(
						painter = painterResource(id = item.iconId),
						contentDescription = null,
					)
				},
				label = {
					Text(
						text = item.name,
						fontSize = 13.sp,
					)
				}
			)
		}
	}
}