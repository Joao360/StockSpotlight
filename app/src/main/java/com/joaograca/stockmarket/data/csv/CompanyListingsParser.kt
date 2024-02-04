package com.joaograca.stockmarket.data.csv

import com.joaograca.stockmarket.domain.model.CompanyListing
import com.opencsv.CSVReader
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

class CompanyListingsParser @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) : CSVParser<CompanyListing> {
    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(dispatcher) {
            csvReader.readAll()
                .drop(1)
                .mapNotNull { line ->
                    val symbol = line.getOrNull(0) ?: return@mapNotNull null
                    val name = line.getOrNull(1) ?: return@mapNotNull null
                    val exchange = line.getOrNull(2) ?: return@mapNotNull null
                    CompanyListing(name = name, symbol = symbol, exchange = exchange)
                }
                .also {
                    csvReader.close()
                }
        }
    }
}