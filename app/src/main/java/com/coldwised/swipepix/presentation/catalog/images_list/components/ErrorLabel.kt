package com.coldwised.swipepix.presentation.catalog.images_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.coldwised.swipepix.R
import com.coldwised.swipepix.util.UiText

@Composable
fun ErrorLabel(
    error: UiText,
    modifier: Modifier,
    onRefreshClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = error.asString(),
            textAlign = TextAlign.Center,
        )
        TextButton(
            onClick = onRefreshClick,
        ) {
            Text(
                text = stringResource(R.string.refresh_page_button_text),
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}