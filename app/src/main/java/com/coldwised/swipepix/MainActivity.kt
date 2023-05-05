package com.coldwised.swipepix

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.coldwised.swipepix.domain.type.BottomNavItem
import com.coldwised.swipepix.presentation.BottomNavigationBar
import com.coldwised.swipepix.presentation.navigation.AppNavigation
import com.coldwised.swipepix.presentation.navigation.destination.PROFILE_SCREEN_ROUTE
import com.coldwised.swipepix.presentation.navigation.destination.cart.CART_GRAPH_NAME
import com.coldwised.swipepix.presentation.navigation.destination.cart.navigateToCartGraph
import com.coldwised.swipepix.presentation.navigation.destination.catalog.CATALOG_GRAPH_NAME
import com.coldwised.swipepix.presentation.navigation.destination.catalog.navigateToCatalogGraph
import com.coldwised.swipepix.presentation.navigation.destination.favorites.FAVORITES_GRAPH_NAME
import com.coldwised.swipepix.presentation.navigation.destination.favorites.navigateToFavoritesGraph
import com.coldwised.swipepix.presentation.navigation.destination.navigateToProfile
import com.coldwised.swipepix.ui.theme.SwipePixTheme
import com.coldwised.swipepix.util.Extension.isCompatibleWithApi33
import com.coldwised.swipepix.util.Extension.shouldUseDarkTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var navHostController: NavHostController

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("onCreate", "onCreateMain")
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val activityState by viewModel.activityState.collectAsState()
            val navController = rememberNavController()
            navHostController = navController
            if(isCompatibleWithApi33()) {
                val notificationPermissionState = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
                if(!notificationPermissionState.status.isGranted) {
                    SideEffect {
                        notificationPermissionState.launchPermissionRequest()
                    }
                }
            }
            val themeStyle = activityState.themeStyle
            TransparentSystemBars(shouldUseDarkTheme(themeStyle = themeStyle))
            when (activityState.isLoading) {
                true -> SwipePixTheme {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                }
                false -> SwipePixTheme(
                    darkTheme = shouldUseDarkTheme(themeStyle = themeStyle),
                    dynamicColor = activityState.useDynamicColors
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(
                                        name = stringResource(id = R.string.catalog_screen_name),
                                        route = CATALOG_GRAPH_NAME,
                                        iconId = R.drawable.ic_search_24,
                                        onClick = navController::navigateToCatalogGraph
                                    ),
                                    BottomNavItem(
                                        name = stringResource(id = R.string.cart_screen_name),
                                        route = CART_GRAPH_NAME,
                                        iconId = R.drawable.ic_shopping_basket_24,
                                        onClick = navController::navigateToCartGraph
                                    ),
                                    BottomNavItem(
                                        name = stringResource(id = R.string.favorites_screen_name),
                                        route = FAVORITES_GRAPH_NAME,
                                        iconId = R.drawable.ic_favorite_border_24,
                                        onClick = navController::navigateToFavoritesGraph
                                    ),
                                    BottomNavItem(
                                        name = stringResource(id = R.string.profile_screen_name),
                                        route = PROFILE_SCREEN_ROUTE,
                                        iconId = R.drawable.ic_account_circle_24,
                                        onClick = navController::navigateToProfile
                                    ),
                                ),
                                navController = navController,
                            )
                        }
                    ) { innerPadding ->
                        Surface(
                            modifier = Modifier
                                .padding(bottom = innerPadding.calculateBottomPadding())
                                .fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            AppNavigation(navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TransparentSystemBars(isDarkTheme: Boolean) {
    val systemUiController = rememberSystemUiController()
    val transparentColor = Color.Transparent

    SideEffect {
        systemUiController.setSystemBarsColor(transparentColor)
        systemUiController.setNavigationBarColor(
            darkIcons = !isDarkTheme,
            color = transparentColor,
            navigationBarContrastEnforced = false,
        )
        systemUiController.setStatusBarColor(
            color = transparentColor,
            darkIcons = !isDarkTheme
        )
    }
}