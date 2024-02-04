package com.joaograca.stockmarket.data.remote

import com.joaograca.stockmarket.BuildConfig
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(@Query("apikey") apiKey: String = STOCK_API_KEY): ResponseBody

    companion object {
        private const val STOCK_API_KEY = BuildConfig.STOCK_API_KEY
    }
}