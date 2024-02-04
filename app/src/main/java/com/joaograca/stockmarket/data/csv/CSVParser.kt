package com.joaograca.stockmarket.data.csv

import java.io.InputStream

interface CSVParser <T> {
    suspend fun parse(stream: InputStream): List<T>
}