package com.cardioguard.evolution.feature.auth.state

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val username: String = "",
    val error: String? = null
)
