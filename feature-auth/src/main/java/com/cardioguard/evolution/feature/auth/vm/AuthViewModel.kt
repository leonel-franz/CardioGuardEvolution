package com.cardioguard.evolution.feature.auth.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardioguard.evolution.feature.auth.repo.AuthRepository
import com.cardioguard.evolution.feature.auth.repo.AuthResult
import com.cardioguard.evolution.feature.auth.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState(isLoading = true)
            when (val r = repo.login(email, password)) {
                is AuthResult.Success -> {
                    _uiState.value = LoginUiState(isLoading = false, isSuccess = true, username = r.username)
                }
                is AuthResult.Error -> {
                    _uiState.value = LoginUiState(isLoading = false, error = r.message)
                }
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState(isLoading = true)
            when (val r = repo.register(name, email, password)) {  // Solo 3 argumentos
                is AuthResult.Success -> _uiState.value = LoginUiState(isLoading = false, isSuccess = true, username = r.username)
                is AuthResult.Error -> _uiState.value = LoginUiState(isLoading = false, error = r.message)
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
