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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
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
	onSearchShow: () -> Unit,
	onSearchHide: () -> Unit,
	onBackClick: () -> Unit,
	scrollBehavior: TopAppBarScrollBehavior,
) {
	if(!backIconVisible) {
		Column(
			modifier = Modifier
				.statusBarsPadding()
				.fillMaxWidth(),
		) {
			val focusRequester = remember { FocusRequester() }
			CenterAlignedTopAppBar(
				title = {
					MyTextField(
						focusRequester = focusRequester,
						searchQuery = searchQuery,
						onSearchQueryChanged = onSearchQueryChanged,
						onSearchShow = onSearchShow,
						onSearchHide = onSearchHide
					)
				},
				scrollBehavior = scrollBehavior,
			)
			Text(
				modifier = Modifier.padding(start = 18.dp, top = 10.dp, bottom = 8.dp),
				text = title ?: stringResource(R.string.categories_topbar_title),
				style = MaterialTheme.typography.titleLarge,
				fontSize = 20.sp,
			)
		}
	} else {
		val focusRequester = remember { FocusRequester() }
		var searchVisible by remember {
			mutableStateOf(false)
		}
		if(!searchVisible) {
			CenterAlignedTopAppBar(
				title = {
					Text(
						text = title ?: stringResource(R.string.categories_topbar_title),
						style = MaterialTheme.typography.titleMedium
					)
				},
				navigationIcon = {
					IconButton(onClick = onBackClick) {
						Icon(
							imageVector = Icons.Default.ArrowBack,
							contentDescription = null
						)
					}
				},
				actions = {
					IconButton(onClick = {
						searchVisible = !searchVisible
						focusRequester.requestFocus()
					}) {
						Icon(
							imageVector = Icons.Default.Search,
							contentDescription = null
						)
					}
				}
			)
		} else {
			CenterAlignedTopAppBar(
				title = {
					MyTextField(
						focusRequester = focusRequester,
						searchQuery = searchQuery,
						onSearchQueryChanged = onSearchQueryChanged,
						onSearchShow = onSearchShow,
						onSearchHide = onSearchHide
					)
				},
				scrollBehavior = scrollBehavior,
			)
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyTextField(
	focusRequester: FocusRequester,
	searchQuery: String,
	onSearchQueryChanged: (String) -> Unit,
	onSearchShow: () -> Unit,
	onSearchHide: () -> Unit,
) {
	BasicTextField(
		modifier = Modifier
			.focusRequester(focusRequester)
			.padding(horizontal = 6.dp)
			.fillMaxWidth()
			.height(40.dp)
			.onFocusChanged {
				if(it.isFocused)
				{
					onSearchShow()
				}
				else
				{
					onSearchHide()
				}
			},
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
						text = "Найти товар"
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
}