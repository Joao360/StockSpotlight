package com.joaograca.stockmarket.ui.companyInfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaograca.stockmarket.domain.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: StockRepository
) : ViewModel() {

    private val symbol: String? = savedStateHandle.get<String>("symbol")

    private val _state = MutableStateFlow(CompanyInfoState())
    val state: StateFlow<CompanyInfoState> = _state
        .onStart {
            symbol?.let { loadCompanyInfo(it) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = CompanyInfoState(isLoading = true)
        )

    private fun loadCompanyInfo(symbol: String) {
        viewModelScope.launch {
            _state.value = CompanyInfoState(isLoading = true)
            val companyInfoResult = async { repository.getCompanyInfo(symbol) }
            val intradayInfoResult = async { repository.getIntradayInfo(symbol) }

            val result = companyInfoResult.await()
            when {
                result.isSuccess -> {
                    _state.update {
                        it.copy(
                            company = result.getOrNull(),
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }

                result.isFailure -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.exceptionOrNull()?.message,
                            company = null
                        )
                    }
                }

                else -> Unit
            }

            val intraday = intradayInfoResult.await()
            when {
                intraday.isSuccess -> {
                    _state.update {
                        it.copy(
                            stockInfo = intraday.getOrThrow(),
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }

                intraday.isFailure -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = intraday.exceptionOrNull()?.message,
                            company = null
                        )
                    }
                }

                else -> Unit
            }
        }
    }
}