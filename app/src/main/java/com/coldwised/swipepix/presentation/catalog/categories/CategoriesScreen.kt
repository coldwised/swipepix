package com.coldwised.swipepix.presentation.catalog.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.coldwised.swipepix.presentation.catalog.categories.component.CategoriesTopBar

@Composable
fun CategoriesScreen(navController: NavController) {
	Scaffold(
		topBar = {
			CategoriesTopBar()
		}
	) { innerPadding ->
		Box(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize()
		) {}
	}
}