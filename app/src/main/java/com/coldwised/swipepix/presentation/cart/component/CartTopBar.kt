package com.coldwised.swipepix.presentation.cart.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.coldwised.swipepix.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartTopBar() {
	TopAppBar(
		title = {
			Text(
				text = stringResource(R.string.cart_topbar_title),
				style = MaterialTheme.typography.titleMedium,
			)
		}
	)
}