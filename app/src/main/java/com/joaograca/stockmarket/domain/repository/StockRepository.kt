package com.joaograca.stockmarket.domain.repository

import com.joaograca.stockmarket.domain.model.CompanyListing
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun refreshCompanyListings(): Result<Unit>

    fun getCompanyListings(
        query: String
    ): Flow<List<CompanyListing>>
}