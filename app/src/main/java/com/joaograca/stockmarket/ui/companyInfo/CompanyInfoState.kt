package com.joaograca.stockmarket.ui.companyInfo

import com.joaograca.stockmarket.domain.model.CompanyInfo
import com.joaograca.stockmarket.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfo: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
