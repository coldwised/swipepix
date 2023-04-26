package com.coldwised.swipepix.presentation.catalog.categories.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.coldwised.swipepix.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesTopBar() {
	TopAppBar(
		title = {
			Text(
				text = stringResource(R.string.categories_topbar_title),
				style = MaterialTheme.typography.titleLarge
			)
		}
	)
}