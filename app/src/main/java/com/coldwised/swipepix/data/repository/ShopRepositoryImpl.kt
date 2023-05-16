package com.coldwised.swipepix.data.repository

import com.coldwised.swipepix.R
import com.coldwised.swipepix.data.local.dao.CartProductsDao
import com.coldwised.swipepix.data.remote.ShopServiceApi
import com.coldwised.swipepix.data.remote.dto.CategoryDto
import com.coldwised.swipepix.data.remote.dto.ProductDto
import com.coldwised.swipepix.domain.repository.ShopRepository
import com.coldwised.swipepix.util.Resource
import com.coldwised.swipepix.util.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val shopApi: ShopServiceApi,
    private val dao: CartProductsDao,
): ShopRepository {
    override fun getCatalogCategories(): Flow<Resource<List<CategoryDto>>> {
        return flow {
            emit(
                safeApiCall {
                    shopApi.getCatalogCategories()
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    override fun getChildCategories(parentId: String): Flow<Resource<List<CategoryDto>>> {
        return flow {
            emit(
                safeApiCall {
                    shopApi.getChildCategories(parentId)
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    override fun getProductsByCategory(categoryId: String): Flow<Resource<List<ProductDto>>> {
        return flow {
            emit(
                safeApiCall {
                    val dao = dao
                    shopApi.getProductsByCategory(categoryId).map {
                        val images = it.images.let { images ->
                            if(it.images.isEmpty()) {
                                listOf("https://media.istockphoto.com/id/924949200/vector/404-error-page-or-file-not-found-icon.jpg?s=170667a&w=0&k=20&c=gsR5TEhp1tfg-qj1DAYdghj9NfM0ldfNEMJUfAzHGtU=")
                            } else {
                                images
                            }
                        }
                        val inCart = dao.hasItem(it.id)
                        it.copy(
                            inCart = inCart,
                            images = images,
                        )
                    }
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    private inline fun <T> safeApiCall(apiCall: () -> T): Resource<T> {
        return try {
            val data = apiCall()
            Resource.Success(data = data)
        } catch(throwable: Throwable) {
            Resource.Error(handleThrowableException(throwable))
        }
    }

    private fun handleThrowableException(throwable: Throwable): UiText {
        return when(throwable) {
            is HttpException -> {
                val localizedMessage = throwable.localizedMessage
                if(localizedMessage.isNullOrEmpty()) {
                    UiText.StringResource(R.string.unknown_exception)
                }
                else {
                    UiText.DynamicString(localizedMessage)
                }
            }
            is IOException -> {
                UiText.StringResource(R.string.io_exception)
            }
            else -> UiText.StringResource(R.string.unknown_exception)
        }
    }

}