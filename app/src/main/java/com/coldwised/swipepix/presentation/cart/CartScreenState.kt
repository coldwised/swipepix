package com.coldwised.swipepix.presentation.cart

import com.coldwised.swipepix.domain.model.CartProduct
import com.coldwised.swipepix.util.UiText

data class CartScreenState(
    val isLoading: Boolean = true,
    val error: UiText? = null,
    val products: List<CartProduct>? = null,
)