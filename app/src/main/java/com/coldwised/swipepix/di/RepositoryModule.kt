package com.coldwised.swipepix.di

import com.coldwised.swipepix.data.repository.ShopRepositoryImpl
import com.coldwised.swipepix.domain.repository.ShopRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindShopRepository(
        repositoryImpl: ShopRepositoryImpl
    ): ShopRepository
}