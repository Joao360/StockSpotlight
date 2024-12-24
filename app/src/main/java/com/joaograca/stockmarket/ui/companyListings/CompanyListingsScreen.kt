package com.joaograca.stockmarket.ui.companyListings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joaograca.stockmarket.domain.model.CompanyListing
import com.joaograca.stockmarket.ui.theme.StockSpotlightTheme

@Composable
fun CompanyListingsRoute(
    viewModel: CompanyListingsViewModel = hiltViewModel(),
    onClickCompany: (CompanyListing) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CompanyListingsScreen(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onClickCompany = onClickCompany,
        onRefresh = viewModel::refresh
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyListingsScreen(
    uiState: CompanyListingsUiState,
    onSearchQueryChange: (String) -> Unit,
    onClickCompany: (CompanyListing) -> Unit,
    onRefresh: () -> Unit
) {
    PullToRefreshBox(isRefreshing = uiState.isLoading, onRefresh = onRefresh) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                placeholder = {
                    Text(text = "Search...")
                },
                maxLines = 1,
                singleLine = true
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(uiState.companies.size) { index ->
                    val company = uiState.companies[index]
                    CompanyItem(
                        company = company,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onClickCompany(company) }
                            .padding(16.dp)
                    )

                    if (index < uiState.companies.size) {
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    }

                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCompanyListing() {
    StockSpotlightTheme {
        CompanyListingsScreen(
            uiState = CompanyListingsUiState(
                companies = listOf(
                    CompanyListing(
                        symbol = "AAPL",
                        name = "Apple Inc.",
                        exchange = "NASDAQ",
                    )
                ),
                isLoading = false,
                searchQuery = ""
            ),
            onSearchQueryChange = {},
            onClickCompany = {},
            onRefresh = { }
        )
    }
}
