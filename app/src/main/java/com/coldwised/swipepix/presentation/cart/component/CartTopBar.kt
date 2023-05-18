package com.coldwised.swipepix.presentation.cart.component

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.coldwised.swipepix.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartTopBar() {
	CenterAlignedTopAppBar(
		title = {
			Text(
				text = stringResource(R.string.cart_topbar_title),
				style = MaterialTheme.typography.titleMedium,
			)
		}
	)
}