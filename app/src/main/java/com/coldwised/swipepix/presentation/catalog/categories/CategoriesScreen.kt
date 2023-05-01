package com.coldwised.swipepix.presentation.catalog.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.coldwised.swipepix.presentation.catalog.categories.component.CategoriesTopBar

@Composable
fun CategoriesScreen(
	navController: NavController,
	viewModel: CategoriesViewModel = hiltViewModel()
) {
	Scaffold(
		topBar = {
			CategoriesTopBar()
		}
	) { innerPadding ->
		val state = viewModel.state.collectAsState().value
		Box(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize()
		) {
			if(state.isLoading) {
				CircularProgressIndicator()
			}
			if(!state.isLoading && state.error == null) {

			}
		}
	}
}