package com.coldwised.swipepix.presentation

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.coldwised.swipepix.domain.type.BottomNavItem
import com.coldwised.swipepix.ui.theme.bottomBarIndicator

@Composable
fun BottomNavigationBar(
	items: List<BottomNavItem>,
	navController: NavController,
	modifier: Modifier = Modifier,
) {
	val currentDestination = navController.currentBackStackEntryAsState().value?.destination
	NavigationBar(
		modifier = modifier
			//.navigationBarsPadding()
			//.height(78.dp)
			.shadow(4.dp),
		//windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp),
	) {
		val colorScheme = MaterialTheme.colorScheme
		items.forEach { item ->
			val selected = currentDestination?.hierarchy?.any {
				it.route == item.route
			} ?: false
			val interactionSource = remember {
				MutableInteractionSource()
			}
			val isPressed = interactionSource.collectIsPressedAsState().value
			NavigationBarItem(
				selected = selected,
				onClick = item.onClick,
				colors = NavigationBarItemDefaults.colors(
					indicatorColor = colorScheme.bottomBarIndicator,
					selectedTextColor = colorScheme.primary,
					selectedIconColor = colorScheme.primary,
					//indicatorColor = colorScheme.tertiary,
					unselectedTextColor = if(!isPressed) colorScheme.outline else colorScheme.primary,
					unselectedIconColor = if(!isPressed) colorScheme.outline else colorScheme.primary,
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
						fontWeight = if(selected) FontWeight.SemiBold else FontWeight.Medium
					)
				},
				interactionSource = interactionSource
			)
		}
	}
}