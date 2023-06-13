package com.coldwised.swipepix.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.coldwised.swipepix.presentation.cart.component.CartTopBar
import com.coldwised.swipepix.util.UiText
import com.coldwised.swipepix.R
import com.coldwised.swipepix.domain.model.CartProduct
import com.coldwised.swipepix.presentation.catalog.images_list.components.ErrorLabel

@Composable
internal fun CartScreen(
    viewModel: CartViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    CartScreen(
        products = state.products.orEmpty(),
        isLoading = state.isLoading,
        error = state.error,
        orderPrice = state.products.orEmpty().let { cartProducts -> if(cartProducts.isEmpty()) null else cartProducts.map { it.price * it.amount }.sum() },
        onDecreaseProductAmount = viewModel::onDecreaseProductAmount,
        onIncreaseProductAmount = viewModel::onIncreaseProductAmount,
        onDeleteProduct = viewModel::onDeleteProduct,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CartScreen(
    products: List<CartProduct>,
    isLoading: Boolean,
    error: UiText?,
    orderPrice: Float?,
    onDecreaseProductAmount: (String) -> Unit,
    onIncreaseProductAmount: (String) -> Unit,
    onDeleteProduct: (String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CartTopBar(
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            CheckoutLabel(
                orderPrice = orderPrice,
                onCheckoutClick = {}
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if(isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            } else if(error != null) {
                ErrorLabel(error = error,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .padding(horizontal = 16.dp),
                    onRefreshClick = {}
                )
            } else if(products.isEmpty()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = stringResource(R.string.empty_cart_text),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                )
            } else {
                ProductList(
                    products = products,
                    onDecreaseProductAmount = onDecreaseProductAmount,
                    onIncreaseProductAmount = onIncreaseProductAmount,
                    onDeleteProduct = onDeleteProduct,
                )
            }
        }
    }
}

@Composable
private fun ProductList(
    products: List<CartProduct>,
    onDecreaseProductAmount: (String) -> Unit,
    onIncreaseProductAmount: (String) -> Unit,
    onDeleteProduct: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(products) { product ->
            ProductItem(
                product = product,
                onDecreaseProductAmount = onDecreaseProductAmount,
                onIncreaseProductAmount = onIncreaseProductAmount,
                onDeleteProduct = onDeleteProduct,
            )
            Divider(thickness = 0.dp)
        }
    }
}

@Composable
private fun CheckoutLabel(
    orderPrice: Float?,
    onCheckoutClick: () -> Unit,
) {
    if(orderPrice == null)
        return
    Column {
        Divider(thickness = 0.dp)
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.checkout_text),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.price_text, orderPrice).replace(',', ' '),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 23.sp
                )
                Button(
                    modifier = Modifier
                        .height(34.dp)
                    ,
                    shape = RoundedCornerShape(9.dp),
                    onClick = onCheckoutClick
                ) {
                    Text(
                        text = stringResource(R.string.checkout_button_text),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
        Divider(thickness = 0.dp)
    }
}

@Composable
private fun ProductItem(
    product: CartProduct,
    onDecreaseProductAmount: (String) -> Unit,
    onIncreaseProductAmount: (String) -> Unit,
    onDeleteProduct: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .weight(0.2f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.White)
                    ,
            model = product.image,
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Column(
            modifier = Modifier
                .weight(0.8f)
                .padding(start = 12.dp)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 4.dp),
                text = stringResource(id = R.string.price_text, product.price).replace(',', ' '),
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                        ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Counter(
                    count = product.amount,
                    onPlusClick = { onIncreaseProductAmount(product.id) },
                    onMinusClick = { onDecreaseProductAmount(product.id) }
                )
                IconButton(
                    onClick = { onDeleteProduct(product.id) }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(22.dp)
                        ,
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = stringResource(R.string.delete_from_cart)
                    )
                }
            }
        }
    }
}

@Composable
private fun Counter(
    count: Int,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        FilledTonalIconButton(
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .size(30.dp)
                    ,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            onClick = onMinusClick
        ) {
            Icon(
                modifier = Modifier
                    .size(16.dp)
                ,
                painter = painterResource(id = R.drawable.ic_minus_24),
                contentDescription = stringResource(R.string.decrease_one),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Text(
            modifier = Modifier
                .padding(horizontal = 9.dp),
            text = "$count шт.",
            style = MaterialTheme.typography.titleMedium
        )
        FilledTonalIconButton(
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .size(30.dp)
            ,
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            onClick = onPlusClick
        ) {
            Icon(
                modifier = Modifier
                    .size(16.dp)
                ,
                painter = painterResource(id = R.drawable.ic_plus_24),
                contentDescription = stringResource(R.string.increase_one),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}