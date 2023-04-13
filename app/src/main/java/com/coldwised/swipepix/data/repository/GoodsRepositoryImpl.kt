package com.coldwised.swipepix.data.repository

import com.coldwised.swipepix.R
import com.coldwised.swipepix.data.mappers.toOfferModel
import com.coldwised.swipepix.data.remote.GoodsApi
import com.coldwised.swipepix.domain.model.OfferModel
import com.coldwised.swipepix.domain.repository.GoodsRepository
import com.coldwised.swipepix.util.Resource
import com.coldwised.swipepix.util.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GoodsRepositoryImpl @Inject constructor(
    private val goodsApi: GoodsApi,
): GoodsRepository {
    override fun getAllGoods(): Flow<Resource<List<OfferModel>>> {
        return flow {
            emit(
                safeApiCall {
                    val file =  goodsApi.getAllGoods()
                    //val asdgf = file.shop?.categories
                    file.shop?.offers?.map { it.toOfferModel() } ?: emptyList()
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