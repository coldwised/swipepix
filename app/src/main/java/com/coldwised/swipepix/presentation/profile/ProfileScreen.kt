package com.coldwised.swipepix.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.coldwised.swipepix.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
	onNavigateToThemeSettings: () -> Unit,
) {
	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(
						text = stringResource(id = R.string.profile_screen_name)
					)
				}
			)
		}
	) { innerPadding ->
		Box(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize()
		) {
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.verticalScroll(
						enabled = true,
						state = rememberScrollState()
					),
				horizontalAlignment = CenterHorizontally
			) {
				Card(
					modifier = Modifier
						.padding(16.dp),
					colors = CardDefaults.cardColors(
						contentColor = MaterialTheme.colorScheme.onSurface
					)
				) {
					Column(
						modifier = Modifier
							.fillMaxWidth()
							.padding(16.dp),
						horizontalAlignment = CenterHorizontally
					) {
						Text(
							modifier = Modifier.padding(vertical = 12.dp),
							text = stringResource(R.string.bonus_title),
							style = MaterialTheme.typography.titleLarge,
							textAlign = TextAlign.Center
						)
						Text(
							modifier = Modifier.padding(bottom = 6.dp),
							text = stringResource(R.string.bonus_text),
							textAlign = TextAlign.Center
						)
						TextButton(
							onClick = {/*TODO*/}
						) {
							Text(
								color = MaterialTheme.colorScheme.tertiary,
								text = stringResource(R.string.auth_button_text),
								style = MaterialTheme.typography.titleMedium,
							)
						}
					}
				}
				ListItem(
					modifier = Modifier.clickable(
						enabled = true,
						onClick = onNavigateToThemeSettings,
					),
					headlineContent = {
						Text(
							text = stringResource(id = R.string.app_theme_title_text)
						)
					},
					trailingContent = {
						Icon(
							imageVector = Icons.Default.KeyboardArrowRight,
							contentDescription = null
						)
					}
				)
				Divider(thickness = 0.dp, modifier = Modifier.padding(horizontal = 16.dp))
				ListItem(
					modifier = Modifier.clickable(
						enabled = true,
						onClick = onNavigateToThemeSettings,
					),
					headlineContent = {
						Text(
							text = stringResource(R.string.about_app_text)
						)
					},
					trailingContent = {
						Icon(
							imageVector = Icons.Default.KeyboardArrowRight,
							contentDescription = null
						)
					}
				)
				Divider(thickness = 0.dp, modifier = Modifier.padding(horizontal = 16.dp))
			}
		}
	}
}