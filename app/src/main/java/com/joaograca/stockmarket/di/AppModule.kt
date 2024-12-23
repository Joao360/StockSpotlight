package com.joaograca.stockmarket.di

import android.content.Context
import androidx.room.Room
import com.joaograca.stockmarket.data.csv.CSVParser
import com.joaograca.stockmarket.data.csv.CompanyListingsParser
import com.joaograca.stockmarket.data.local.StockDao
import com.joaograca.stockmarket.data.local.StockDatabase
import com.joaograca.stockmarket.data.remote.StockApi
import com.joaograca.stockmarket.domain.model.CompanyListing
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): StockApi {
        return Retrofit.Builder()
            .baseUrl("https://alphavantage.co")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create<StockApi>()
    }

    @Provides
    @Singleton
    fun provideStockDatabase(@ApplicationContext context: Context): StockDatabase {
        return Room.databaseBuilder(context, StockDatabase::class.java, "stock.db").build()
    }

    @Provides
    @Singleton
    fun provideStockDao(stockDatabase: StockDatabase): StockDao {
        return stockDatabase.dao
    }
}