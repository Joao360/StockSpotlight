package com.joaograca.stockmarket.data.repository

import com.joaograca.stockmarket.data.csv.CSVParser
import com.joaograca.stockmarket.data.local.StockDao
import com.joaograca.stockmarket.data.mapper.toCompanyInfo
import com.joaograca.stockmarket.data.mapper.toDomain
import com.joaograca.stockmarket.data.mapper.toEntity
import com.joaograca.stockmarket.data.remote.StockApi
import com.joaograca.stockmarket.domain.model.CompanyInfo
import com.joaograca.stockmarket.domain.model.CompanyListing
import com.joaograca.stockmarket.domain.model.IntradayInfo
import com.joaograca.stockmarket.domain.repository.StockRepository
import com.joaograca.stockmarket.util.suspendRunCatching
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val dao: StockDao,
    private val companyListingsParser: CSVParser<CompanyListing>,
    private val intradayInfoParser: CSVParser<IntradayInfo>,
) : StockRepository {
    override suspend fun refreshCompanyListings(): Result<Unit> = suspendRunCatching {
        val response = api.getListings()
        val companyListings = companyListingsParser.parse(response.byteStream())

        dao.clearCompanyListings()
        dao.insertCompanyListings(companyListings.map { it.toEntity() })
    }

    override fun getCompanyListings(query: String): Flow<List<CompanyListing>> {
        return dao.searchCompanyListing(query)
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

    override suspend fun getIntradayInfo(symbol: String): Result<List<IntradayInfo>> =
        suspendRunCatching {
            val responseBody = api.getIntradayInfo(symbol)
            intradayInfoParser.parse(responseBody.byteStream())
        }

    override suspend fun getCompanyInfo(symbol: String): Result<CompanyInfo> = suspendRunCatching {
        api.getCompanyInfo(symbol).toCompanyInfo()
    }
}