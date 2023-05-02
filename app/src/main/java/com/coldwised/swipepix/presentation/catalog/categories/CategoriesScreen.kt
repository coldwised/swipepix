package com.coldwised.swipepix.presentation.catalog.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.coldwised.swipepix.data.remote.dto.CategoryDto
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
				CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
			}
			if(!state.isLoading && state.error == null) {
				//CategoriesList(state.categories)
			}
		}
	}
}

@Composable
fun CategoriesList(categories: List<CategoryDto>, onItemClick: (Int) -> Unit) {
	LazyVerticalGrid(
		modifier = Modifier
			.fillMaxSize(),
		state = rememberLazyGridState(),
		columns = GridCells.Adaptive(110.dp),
	) {
		items(categories) { category ->
			Card(
				modifier = Modifier
					.clickable {
						onItemClick(category.id)
					}
			) {
				Column {
					AsyncImage(
						model = category.categoryImage,
						contentDescription = null,
					)
					Text(
						text = category.name
					)
				}
			}
		}
	}
}