package com.coldwised.swipepix.presentation.catalog.categories.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
	Column(
		modifier = Modifier
			.statusBarsPadding()
			.fillMaxWidth(),
	) {
		CenterAlignedTopAppBar(
			title = {
				BasicTextField(
					modifier = Modifier
						.fillMaxWidth()
						.height(40.dp),
					value = searchQuery,
					onValueChange = onSearchQueryChanged,
					textStyle = TextStyle(
						fontSize = 20.sp,
						color = MaterialTheme.colorScheme.onSurfaceVariant,
					),
					cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurfaceVariant),
					decorationBox = { innerTextField ->
						OutlinedTextFieldDefaults.DecorationBox(
							value = searchQuery,
							innerTextField = innerTextField,
							enabled = true,
							singleLine = true,
							visualTransformation = VisualTransformation.None,
							interactionSource = MutableInteractionSource(),
							placeholder = {
								Text(
									text = "Поиск"
								)
							},
							leadingIcon = {
								Icon(
									imageVector = Icons.Default.Search,
									contentDescription = null,
									tint = MaterialTheme.colorScheme.onSurfaceVariant,
								)
							},
							trailingIcon = {
								if(searchQuery.isNotEmpty()) {
									IconButton(
										onClick = {/*TODO*/},
									) {
										Icon(
											contentDescription = null,
											tint = MaterialTheme.colorScheme.onSurfaceVariant,
											imageVector = Icons.Default.Clear,
										)
									}
								}
							},
							colors = OutlinedTextFieldDefaults.colors(),
							contentPadding = PaddingValues(0.dp),
							container = {
								Box(
									modifier = Modifier
										.fillMaxWidth()
										.clip(RoundedCornerShape(12.dp))
										.background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)),
								)
							},
						)
					},
					singleLine = true,
				)
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
		)
		Text(
			modifier = Modifier.padding(start = 12.dp),
			text = title ?: stringResource(R.string.categories_topbar_title),
			style = MaterialTheme.typography.titleLarge,
		)
	}
}