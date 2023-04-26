package com.coldwised.swipepix.di

import com.coldwised.swipepix.data.remote.GoodsApi
import com.coldwised.swipepix.data.remote.ShopServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
//import retrofit2.converter.jaxb.JaxbConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideInterviewApplicationApi(): GoodsApi {
        return Retrofit.Builder()
            .baseUrl(GoodsApi.BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideShopApi(): ShopServiceApi {
        return Retrofit.Builder()
            .baseUrl(ShopServiceApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}