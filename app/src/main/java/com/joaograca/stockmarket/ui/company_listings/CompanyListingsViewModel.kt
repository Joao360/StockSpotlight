package com.joaograca.stockmarket.ui.company_listings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaograca.stockmarket.domain.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class CompanyListingsViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {
    private var refreshJob: Job? = null
    private val localState = MutableStateFlow(CompanyListingsLocalState())

    private val companyListings = localState.map { it.searchQuery }
        .debounce(500L)
        .flatMapLatest { searchQuery ->
            repository.getCompanyListings(searchQuery)
        }

    val uiState = combine(
        localState, companyListings
    ) { localState, companyListings ->
        CompanyListingsLocalUiState(
            companies = companyListings,
            isLoading = localState.isLoading,
            searchQuery = localState.searchQuery
        )
    }
        .catch {
            emit(
                CompanyListingsLocalUiState(
                    companies = emptyList(),
                    isLoading = localState.value.isLoading,
                    searchQuery = localState.value.searchQuery
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = CompanyListingsLocalUiState(isLoading = true)
        )

    init {
        refresh()
    }

    fun refresh() {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            localState.update { it.copy(isLoading = true) }

            repository.refreshCompanyListings()
                .onFailure { it.printStackTrace() }

            localState.update { it.copy(isLoading = false) }
        }
    }

    fun onSearchQueryChange(query: String) {
        localState.update { it.copy(searchQuery = query) }
    }
}