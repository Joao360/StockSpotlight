package com.joaograca.stockmarket.data.remote

import com.joaograca.stockmarket.BuildConfig
import com.joaograca.stockmarket.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(@Query("apikey") apiKey: String = STOCK_API_KEY): ResponseBody

    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = STOCK_API_KEY
    ): ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = STOCK_API_KEY
    ): CompanyInfoDto

    companion object {
        private const val STOCK_API_KEY = BuildConfig.STOCK_API_KEY
    }
}