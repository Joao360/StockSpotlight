package com.joaograca.stockmarket.data.repository

import com.joaograca.stockmarket.data.local.StockDao
import com.joaograca.stockmarket.data.mapper.toDomain
import com.joaograca.stockmarket.data.remote.StockApi
import com.joaograca.stockmarket.domain.model.CompanyListing
import com.joaograca.stockmarket.domain.repository.StockRepository
import com.joaograca.stockmarket.util.suspendRunCatching
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val dao: StockDao
) : StockRepository {
    override suspend fun refreshCompanyListings(): Result<Unit> = suspendRunCatching {
        val response = api.getListings()
        // response.byteStream()
    }

    override suspend fun getCompanyListings(query: String): Flow<List<CompanyListing>> {
        return dao.searchCompanyListing(query)
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }
}