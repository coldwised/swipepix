package com.coldwised.swipepix.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldwised.swipepix.domain.use_case.AddProductToCartUseCase
import com.coldwised.swipepix.domain.use_case.GetCartProductsUseCase
import com.coldwised.swipepix.domain.use_case.RemoveProductFromCartUseCase
import com.coldwised.swipepix.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartProductsUseCase: GetCartProductsUseCase,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(CartScreenState())
    val state = _state.asStateFlow()

    fun onDecreaseProductAmount(id: String) {
        viewModelScope.launch {
            _state.update {
                val newProducts = it.products.orEmpty().toMutableList()
                for (i in newProducts.indices) {
                    val product = newProducts[i]
                    if(product.id == id) {
                        removeFromCart(id)
                        if(product.amount <= 1) {
                            newProducts.removeAt(i)
                        } else {
                            newProducts[i] = product.copy(
                                amount = product.amount - 1
                            )
                        }
                    }
                }
                it.copy(
                    products = newProducts
                )
            }
        }
    }

    fun onIncreaseProductAmount(id: String) {
        viewModelScope.launch {
            _state.update {
                val newProducts = it.products.orEmpty().toMutableList()
                for (i in newProducts.indices) {
                    val product = newProducts[i]
                    if(product.id == id) {
                        addToCart(id)
                        newProducts[i] = product.copy(
                            amount = product.amount + 1
                        )
                    }
                }
                it.copy(
                    products = newProducts
                )
            }
        }
    }

    fun onDeleteProduct(id: String) {
        removeFromCart(id, true)
    }

    private fun removeFromCart(id: String, deleteAll: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            removeProductFromCartUseCase(id, deleteAll)
        }
    }

    private fun addToCart(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addProductToCartUseCase(id)
        }
    }

    init {
        viewModelScope.launch {
            getCartProductsUseCase().collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                products = result.data,
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.message,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }
}