package com.coldwised.swipepix.domain.type

import com.coldwised.swipepix.Constants
import com.coldwised.swipepix.R
import com.coldwised.swipepix.util.UiText


enum class Screen(val route: String, val screenName: UiText, val subRoutes: List<String>? = null) {
    ProductsScreen("ProductsScreen/{${Constants.PARENT_CATEGORY_ID_PARAM}", UiText.StringResource(R.string.search_screen_name)),
    CategoriesScreen("CatalogCategories?${Constants.PARENT_CATEGORY_ID_PARAM}={${Constants.PARENT_CATEGORY_ID_PARAM}", UiText.StringResource(R.string.search_screen_name)),
    CartScreen("CartScreen", UiText.StringResource(R.string.cart_screen_name)),
    FavoritesScreen("CartScreen", UiText.StringResource(R.string.favorites_screen_name)),
    ProfileScreen("CartScreen", UiText.StringResource(R.string.profile_screen_name)),
    ThemeSettingsScreen("ThemeSettingsScreen", UiText.StringResource(R.string.app_name)),
    SharedUrlScreen("SharedUrlScreen", UiText.StringResource(R.string.app_name)),
    ;

    fun withArgs(vararg args: String, isNullable: Boolean = false): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}