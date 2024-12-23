package com.joaograca.stockmarket.data.csv

import com.joaograca.stockmarket.data.mapper.toIntradayInfo
import com.joaograca.stockmarket.data.remote.dto.IntradayInfoDto
import com.joaograca.stockmarket.domain.model.CompanyListing
import com.joaograca.stockmarket.domain.model.IntradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject

class IntradayInfoParser @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) : CSVParser<IntradayInfo> {
    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(dispatcher) {
            csvReader.readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null
                    val dto = IntradayInfoDto(
                        timestamp = timestamp,
                        close = close.toDouble()
                    )
                    dto.toIntradayInfo()
                }
                .filter {
                    it.date.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth
                }
                .sortedBy { it.date.hour }
                .also {
                    csvReader.close()
                }
        }
    }
}