package com.coldwised.swipepix.presentation.catalog.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.coldwised.swipepix.data.remote.dto.CategoryDto
import com.coldwised.swipepix.presentation.catalog.categories.component.CategoriesTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
	onCategoryClick: (CategoryDto) -> Unit,
	onThemeSettingsClick: () -> Unit,
	categoryId: String? = null,
	viewModel: CategoriesViewModel = hiltViewModel()
) {
	Scaffold(
		topBar = {
			CategoriesTopBar(
				onThemeSettingsClick = onThemeSettingsClick
			)
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
				CategoriesList(state.categories, onCategoryClick)
			}
		}
	}
}

@Composable
fun CategoriesList(categories: List<CategoryDto>, onCategoryClick: (CategoryDto) -> Unit) {
	LazyColumn(
		modifier = Modifier
			.fillMaxSize(),
	) {
		items(categories) { category ->
			Column(
				modifier = Modifier
					.clickable {
						onCategoryClick(category)
					},
			) {
				ListItem(
					leadingContent = {
						AsyncImage(
							modifier = Modifier
								.clip(RoundedCornerShape(6.dp))
								.size(40.dp)
								.background(Color.White)
							,
							model = category.image,
							contentScale = ContentScale.FillBounds,
							contentDescription = null,
						)
					},
					headlineContent = {
						Text(
							text = category.name,
						)
					},
					trailingContent = {
						Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
					},
				)
				Divider(Modifier.padding(horizontal = 16.dp))
			}
		}
	}
}