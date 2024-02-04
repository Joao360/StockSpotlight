package com.joaograca.stockmarket.ui.company_listings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joaograca.stockmarket.domain.model.CompanyListing

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CompanyListingsRoute(
    viewModel: CompanyListingsViewModel = hiltViewModel(),
    onClickCompany: (CompanyListing) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefreshing,
        onRefresh = viewModel::refresh
    )

    CompanyListingsScreen(
        uiState = uiState,
        pullRefreshState = pullRefreshState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onClickCompany = onClickCompany
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CompanyListingsScreen(
    uiState: CompanyListingsLocalUiState,
    pullRefreshState: PullRefreshState,
    onSearchQueryChange: (String) -> Unit,
    onClickCompany: (CompanyListing) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
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

        PullRefreshIndicator(refreshing = uiState.isRefreshing, state = pullRefreshState)
    }
}
