package com.joaograca.stockmarket.ui

import com.joaograca.stockmarket.domain.model.IntradayInfo
import java.time.LocalDateTime

object PreviewData {
    val intradayInfos = listOf(
        IntradayInfo(
            date = LocalDateTime.parse("2021-01-01T09:00:00"),
            close = 100.0
        ),
        IntradayInfo(
            date = LocalDateTime.parse("2021-01-01T10:00:00"),
            close = 110.0
        ),
        IntradayInfo(
            date = LocalDateTime.parse("2021-01-01T11:00:00"),
            close = 90.0
        ),
        IntradayInfo(
            date = LocalDateTime.parse("2021-01-01T12:00:00"),
            close = 130.0
        ),
        IntradayInfo(
            date = LocalDateTime.parse("2021-01-01T13:00:00"),
            close = 100.0
        ),
        IntradayInfo(
            date = LocalDateTime.parse("2021-01-01T14:00:00"),
            close = 99.0
        ),
        IntradayInfo(
            date = LocalDateTime.parse("2021-01-01T15:00:00"),
            close = 101.0
        ),
        IntradayInfo(
            date = LocalDateTime.parse("2021-01-01T16:00:00"),
            close = 105.0
        ),
        IntradayInfo(
            date = LocalDateTime.parse("2021-01-01T17:00:00"),
            close = 102.0
        ),
        IntradayInfo(
            date = LocalDateTime.parse("2021-01-01T18:00:00"),
            close = 110.0
        ),
    )
}