package com.coldwised.swipepix.domain.use_case

import com.coldwised.swipepix.data.local.dao.CartProductsDao
import javax.inject.Inject

class RemoveProductFromCartUseCase @Inject constructor(
    private val cartProductsDao: CartProductsDao,
){
    suspend operator fun invoke(id: String, deleteAll: Boolean = false) {
        val cartProductsDao = cartProductsDao
        val existingProduct = cartProductsDao.getProduct(id)
        if(existingProduct != null && existingProduct.amount <= 1 || deleteAll) {
            cartProductsDao.deleteProduct(id)
        } else if(existingProduct != null) {
            cartProductsDao.updateProductAmount(id, existingProduct.amount - 1)
        }
    }
}