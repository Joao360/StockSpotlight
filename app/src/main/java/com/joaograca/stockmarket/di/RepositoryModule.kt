package com.joaograca.stockmarket.di

import com.joaograca.stockmarket.data.csv.CSVParser
import com.joaograca.stockmarket.data.csv.CompanyListingsParser
import com.joaograca.stockmarket.data.csv.IntradayInfoParser
import com.joaograca.stockmarket.data.repository.StockRepositoryImpl
import com.joaograca.stockmarket.domain.model.CompanyListing
import com.joaograca.stockmarket.domain.model.IntradayInfo
import com.joaograca.stockmarket.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindRepository(impl: StockRepositoryImpl): StockRepository

    companion object {
        @Provides
        @Singleton
        fun provideCompanyListingsParser(): CSVParser<CompanyListing> {
            return CompanyListingsParser(Dispatchers.IO)
        }

        @Provides
        @Singleton
        fun provideIntradayInfoParser(): CSVParser<IntradayInfo> {
            return IntradayInfoParser(Dispatchers.IO)
        }
    }
}