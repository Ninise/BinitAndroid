package com.ndteam.wasteandroidapp.di

import com.ndteam.wasteandroidapp.repository.WasteRepository
import com.ndteam.wasteandroidapp.repository.WasteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWasteRepository(
        wasteRepositoryImpl: WasteRepositoryImpl
    ): WasteRepository
}