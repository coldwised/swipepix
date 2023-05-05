package com.coldwised.swipepix.presentation.catalog.categories.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
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
	onThemeSettingsClick: () -> Unit,
	scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
) {
	TopAppBar(
		title = {
			Text(
				stringResource(R.string.categories_topbar_title),
			)
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