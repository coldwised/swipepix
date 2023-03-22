package com.coldwised.swipepix.di

import com.coldwised.swipepix.data.local.datastore.UserPreferencesImplDataStore
import com.coldwised.swipepix.domain.datastore.UserPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    @Singleton
    fun bindUserPreferences(
        userPreferences: UserPreferencesImplDataStore
    ): UserPreferences
}