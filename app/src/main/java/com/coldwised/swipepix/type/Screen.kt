package com.coldwised.swipepix.type

import com.coldwised.swipepix.R
import com.coldwised.swipepix.util.UiText


enum class Screen(val route: String, val screenName: UiText, val subRoutes: List<String>? = null) {
    ImagesScreen("ImagesScreen", UiText.StringResource(R.string.app_name)),
    ThemeSettingsScreen("ThemeSettingsScreen", UiText.StringResource(R.string.app_name)),
    SharedUrlScreen("SharedUrlScreen", UiText.StringResource(R.string.app_name)),
    ;

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}