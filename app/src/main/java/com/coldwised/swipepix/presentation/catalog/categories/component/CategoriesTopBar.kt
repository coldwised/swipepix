package com.coldwised.swipepix.presentation.catalog.categories.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.coldwised.swipepix.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesTopBar(
	title: String?,
	searchQuery: String,
	backIconVisible: Boolean,
	onSearchQueryChanged: (String) -> Unit,
	onThemeSettingsClick: () -> Unit,
	onBackClick: () -> Unit,
	scrollBehavior: TopAppBarScrollBehavior,
) {
	TopAppBar(
		title = {
			Column(
				modifier = Modifier
					.fillMaxWidth()
			) {
				TextField(
					value = searchQuery,
					onValueChange = onSearchQueryChanged,
				)
				Text(
					title ?: stringResource(R.string.categories_topbar_title)
				)
			}
		},
		navigationIcon = {
			if(backIconVisible) {
				IconButton(onClick = onBackClick) {
					Icon(
						imageVector = Icons.Default.ArrowBack,
						contentDescription = null
					)
				}
			}
		},
		scrollBehavior = scrollBehavior,
		actions = {
			Column {
				var menuVisible by remember {
					mutableStateOf(false)
				}
				IconButton(
					onClick = {
						menuVisible = !menuVisible
					}
				) {
					Icon(
						imageVector = Icons.Default.MoreVert,
						contentDescription = null,
					)
				}
				DropdownMenu(
					expanded = menuVisible,
					onDismissRequest = {
						menuVisible = false
					}
				) {
					DropdownMenuItem(
						modifier = Modifier.fillMaxSize()
						,
						text = {
							Text(
								text = stringResource(R.string.theme_label_text)
							)
						},
						onClick = onThemeSettingsClick
					)
				}
			}
		}
	)
}