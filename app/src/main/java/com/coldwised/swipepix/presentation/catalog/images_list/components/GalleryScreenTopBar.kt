package com.coldwised.swipepix.presentation.catalog.images_list.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreenTopBar(
    searchQuery: String,
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onSearchQueryChanged: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onSearchShow: () -> Unit,
    onSearchHide: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    var searchVisible by remember {
        mutableStateOf(false)
    }
    BackHandler(
        enabled = searchVisible,
        onBack = {
            searchVisible = !searchVisible
        }
    )
    TopAppBar(
        title = {
            if(searchVisible) {
                LaunchedEffect(true) {
                    focusRequester.requestFocus()
                }
                MyTextField(
                    focusRequester = focusRequester,
                    searchQuery = searchQuery,
                    onSearchQueryChanged = onSearchQueryChanged,
                    onSearchShow = {
                        onSearchShow()
                    },
                    onSearchHide = {
                        onSearchHide()
                    },
                    onSearchClick = onSearchClick,
                )
            } else {
                Text(
                    text = title,
                    maxLines = 1
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    if(!searchVisible) {
                        onBackClick()
                    } else {
                        searchVisible = false
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                )
            }
        },
        actions = {
            if(!searchVisible) {
                IconButton(
                    onClick = {
                        searchVisible = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
        colors = if(searchVisible) TopAppBarDefaults.topAppBarColors(
            scrolledContainerColor = MaterialTheme.colorScheme.surface
        ) else TopAppBarDefaults.topAppBarColors(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyTextField(
    focusRequester: FocusRequester,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearchShow: () -> Unit,
    onSearchHide: () -> Unit,
    onSearchClick: (String) -> Unit,
) {
    var focused by remember {
        mutableStateOf(true)
    }
    BasicTextField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .padding(end = 16.dp)
            .fillMaxWidth()
            .height(40.dp)
            .onFocusChanged {
                focused = if (it.isFocused) {
                    onSearchShow()
                    true
                } else {
                    onSearchHide()
                    false
                }
            },
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        textStyle = TextStyle(
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, autoCorrect = true, imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClick(searchQuery)
            }
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
                leadingIcon = if(!focused) {
                    {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                } else null,
                trailingIcon = {
                    if(searchQuery.isNotEmpty()) {
                        IconButton(
                            onClick = { onSearchQueryChanged("") },
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
                contentPadding = PaddingValues(horizontal = 12.dp),
                container = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
                    )
                },
            )
        },
        singleLine = true,
    )
}