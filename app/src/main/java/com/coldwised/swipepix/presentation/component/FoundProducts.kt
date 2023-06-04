package com.coldwised.swipepix.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coldwised.swipepix.data.remote.dto.ProductDto

@Composable
fun FoundProducts(
    products: List<ProductDto>,
    onItemClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        items(products) { product ->
            ListItem(
                modifier = Modifier
                    .clickable {
                        onItemClick(product.name)
                    },
                headlineContent = {
                    Text(
                        text = product.name
                    )
                }
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.dp)
        }
    }
}