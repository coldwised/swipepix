package com.coldwised.swipepix.presentation.theme_settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coldwised.swipepix.R
import com.coldwised.swipepix.presentation.theme_settings.components.ThemeStyleSection
import com.coldwised.swipepix.util.Extension.isCompatibleWithDynamicColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSettingsScreen(
    onBackClick: () -> Unit,
    viewModel: ThemeSettingsViewModel = hiltViewModel()
) {
    val screenState = viewModel.state.collectAsState().value
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.theme_settings_title_text)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = stringResource(id = R.string.back_hint_text)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
        ) {
            if (isCompatibleWithDynamicColors()) {
                ListItem(
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.use_dynamic_colors_text),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    trailingContent = {
                        Switch(
                            checked = screenState.useDynamicColors,
                            onCheckedChange = { viewModel.toggleDynamicColors() }
                        )
                    }
                )
                Spacer(modifier = Modifier.height(height = 8.dp))
            }

            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = stringResource(R.string.app_theme_title_text),
                style = MaterialTheme.typography.bodyLarge
            )
            ThemeStyleSection(
                modifier = Modifier.padding(horizontal = 32.dp),
                themeStyle = screenState.themeStyle,
                changeThemeStyle = { viewModel.changeThemeStyle(themeStyle = it) }
            )
        }
    }
}