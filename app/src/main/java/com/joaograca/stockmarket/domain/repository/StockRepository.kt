package com.joaograca.stockmarket.domain.repository

import com.joaograca.stockmarket.domain.model.CompanyInfo
import com.joaograca.stockmarket.domain.model.CompanyListing
import com.joaograca.stockmarket.domain.model.IntradayInfo
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun refreshCompanyListings(): Result<Unit>

    fun getCompanyListings(
        query: String
    ): Flow<List<CompanyListing>>

    suspend fun getIntradayInfo(
        symbol: String
    ): Result<List<IntradayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Result<CompanyInfo>
}