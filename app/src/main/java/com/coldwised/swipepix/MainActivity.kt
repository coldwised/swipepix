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
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coldwised.swipepix.domain.type.BottomNavItem
import com.coldwised.swipepix.domain.type.Screen
import com.coldwised.swipepix.presentation.BottomNavigationBar
import com.coldwised.swipepix.presentation.cart.CartScreen
import com.coldwised.swipepix.presentation.gallery.images_list.GalleryScreen
import com.coldwised.swipepix.ui.theme.SwipePixTheme
import com.coldwised.swipepix.util.Extension.isCompatibleWithApi33
import com.coldwised.swipepix.util.Extension.shouldUseDarkTheme
import com.coldwised.swipepix.presentation.theme_settings.ThemeSettingsScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var navHostController: NavHostController

    override fun onDestroy() {
        super.onDestroy()
        Log.e("onCreate", "onDestroy")
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
    }

    @OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
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
                                        screen = Screen.ImagesScreen,
                                        iconId = R.drawable.ic_search_24,
                                    ),
                                    BottomNavItem(
                                        screen = Screen.CartScreen,
                                        iconId = R.drawable.ic_shopping_basket_24,
                                    ),
                                    BottomNavItem(
                                        screen = Screen.FavoritesScreen,
                                        iconId = R.drawable.ic_favorite_border_24,
                                    ),
                                    BottomNavItem(
                                        screen = Screen.ProfileScreen,
                                        iconId = R.drawable.ic_account_circle_24,
                                    ),
                                ),
                                navController = navController,
                            )
                        }
                    ) { innerPadding ->
                        Surface(
                            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()).fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            Navigation(navController)
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

@Composable
fun Navigation(navHostController: NavHostController) {
    val imagesScreenRoute = remember { Screen.ImagesScreen.route }
    NavHost(
        navController = navHostController,
        startDestination = imagesScreenRoute
    ) {
        composable(
            route = imagesScreenRoute
        ) {
            GalleryScreen(
                navController = navHostController
            )
        }
        composable(
            route = Screen.ThemeSettingsScreen.route
        ) {
            ThemeSettingsScreen(
                navController = navHostController
            )
        }
        composable(
            route = Screen.CartScreen.route
        ) {
            CartScreen(
                navController = navHostController
            )
        }
        composable(
            route = Screen.FavoritesScreen.route
        ) {
            CartScreen(
                navController = navHostController
            )
        }
        composable(
            route = Screen.ProfileScreen.route
        ) {
            CartScreen(
                navController = navHostController
            )
        }
    }
}