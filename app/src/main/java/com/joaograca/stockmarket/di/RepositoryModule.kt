package com.joaograca.stockmarket.di

import com.joaograca.stockmarket.data.repository.StockRepositoryImpl
import com.joaograca.stockmarket.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindRepository(impl: StockRepositoryImpl): StockRepository
}