package com.coldwised.swipepix

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coldwised.swipepix.domain.type.Screen
import com.coldwised.swipepix.presentation.gallery_screen.images_list.GalleryScreen
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
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Navigation(navController)
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
    }
}