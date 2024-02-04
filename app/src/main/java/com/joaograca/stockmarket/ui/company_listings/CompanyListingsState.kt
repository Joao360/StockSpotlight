package com.joaograca.stockmarket.ui.company_listings

import com.joaograca.stockmarket.domain.model.CompanyListing

data class CompanyListingsLocalState(
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)

data class CompanyListingsLocalUiState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)
