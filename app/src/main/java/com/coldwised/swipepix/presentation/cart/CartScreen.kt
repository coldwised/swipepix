package com.coldwised.swipepix.presentation.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.coldwised.swipepix.presentation.cart.component.CartTopBar

@Composable
fun CartScreen(
) {
    Scaffold(
        topBar = {
            CartTopBar()
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {}
    }
}