package com.joaograca.stockmarket.util

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val result: T) : UiState<T>()
    data object Error: UiState<Nothing>()
}