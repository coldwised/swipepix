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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.coldwised.swipepix.data.remote.dto.CategoryDto
import com.coldwised.swipepix.data.remote.dto.ProductDto
import com.coldwised.swipepix.presentation.catalog.categories.component.CategoriesTopBar
import com.coldwised.swipepix.util.UiText

@Composable
internal fun CategoriesScreen(
	categoryId: String? = null,
	categoryName: String? = null,
	onCategoryClick: (CategoryDto) -> Unit,
	onBackClick: () -> Unit,
	viewModel: CategoriesViewModel = hiltViewModel()
) {
	LaunchedEffect(key1 = true) {
		viewModel.loadCategories(categoryId)
	}
	val state = viewModel.state.collectAsState().value
	CategoriesScreen(
		onCategoryClick = onCategoryClick,
		onBackClick = onBackClick,
		categoryName = categoryName,
		searchQuery = state.searchQuery,
		foundProducts = state.foundProducts,
		backIconVisible = categoryId != null,
		isLoading = state.isLoading,
		error = state.error,
		categories = state.categories.orEmpty(),
		onSearchQueryChanged = viewModel::onSearchQueryChanged,
		onSearchShow = viewModel::onSearchShow,
		onSearchHide = viewModel::onSearchHide,
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoriesScreen(
	categoryName: String?,
	searchQuery: String,
	isLoading: Boolean,
	backIconVisible: Boolean,
	foundProducts: List<ProductDto>?,
	error: UiText?,
	categories: List<CategoryDto>,
	onSearchHide: () -> Unit,
	onSearchShow: () -> Unit,
	onSearchQueryChanged: (String) -> Unit,
	onCategoryClick: (CategoryDto) -> Unit,
	onBackClick: () -> Unit,
) {
	val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
	Scaffold(
		modifier = Modifier
			.nestedScroll(scrollBehavior.nestedScrollConnection),
		topBar = {
			CategoriesTopBar(
				searchQuery = searchQuery,
				title = categoryName,
				onSearchHide = onSearchHide,
				onSearchShow = onSearchShow,
				onBackClick = onBackClick,
				backIconVisible = backIconVisible,
				onSearchQueryChanged = onSearchQueryChanged,
				scrollBehavior = scrollBehavior
			)
		}
	) { innerPadding ->
		Box(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize()
		) {
			if(isLoading) {
				CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
			}
			if(foundProducts != null) {
				FoundProducts(
					products = foundProducts,
					onItemClick = {}
				)
			} else if(!isLoading && error == null) {
				CategoriesList(categories, onCategoryClick)
			}
		}
	}
}

@Composable
private fun FoundProducts(
	products: List<ProductDto>,
	onItemClick: (String) -> Unit,
) {
	LazyColumn(
		modifier = Modifier
			.fillMaxSize(),
	) {
		items(products) { product ->
			ListItem(
				modifier = Modifier
					.clickable {
						onItemClick(product.id)
					},
				headlineContent = {
					Text(
						text = product.name
					)
				}
			)
		}
	}
}

@Composable
private fun CategoriesList(categories: List<CategoryDto>, onCategoryClick: (CategoryDto) -> Unit) {
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
						category.image?.let {
							AsyncImage(
								modifier = Modifier
									.clip(RoundedCornerShape(6.dp))
									.size(40.dp)
									.background(Color.White)
								,
								model = it,
								contentScale = ContentScale.FillBounds,
								contentDescription = null,
							)
						}
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