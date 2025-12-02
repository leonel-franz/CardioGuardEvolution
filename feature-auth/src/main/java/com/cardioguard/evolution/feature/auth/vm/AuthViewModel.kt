package com.cardioguard.evolution.feature.auth.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardioguard.evolution.data.repository.AuthRepository
import com.cardioguard.evolution.data.repository.AuthResult
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

    // LOGIN
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState(isLoading = true)
            when (val r = repo.login(email, password)) {
                is AuthResult.Success -> {
                    _uiState.value = LoginUiState(
                        isLoading = false,
                        isSuccess = true,
                        username = r.username
                    )
                }
                is AuthResult.Error -> {
                    _uiState.value = LoginUiState(
                        isLoading = false,
                        error = r.message
                    )
                }
            }
        }
    }

    // REGISTER con todos los campos requeridos por tu API PHP
    fun register(
        nombres: String,
        apellidos: String,
        email: String,
        password: String,
        fecha_Nacimiento: String,
        genero: String
    ) {
        viewModelScope.launch {
            _uiState.value = LoginUiState(isLoading = true)
            when (val r = repo.register(nombres, apellidos, email, password, fecha_Nacimiento, genero)) {
                is AuthResult.Success -> {
                    _uiState.value = LoginUiState(
                        isLoading = false,
                        isSuccess = true,
                        username = r.username
                    )
                }
                is AuthResult.Error -> {
                    _uiState.value = LoginUiState(
                        isLoading = false,
                        error = r.message
                    )
                }
            }
        }
    }

    // LIMPIAR ERRORES
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
