package com.joaograca.stockmarket.ui.companyListings

import com.joaograca.stockmarket.domain.model.CompanyListing

data class CompanyListingsLocalState(
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)

data class CompanyListingsUiState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)
