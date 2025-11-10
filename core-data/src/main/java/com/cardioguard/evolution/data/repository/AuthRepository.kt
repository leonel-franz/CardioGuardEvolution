package com.cardioguard.evolution.data.repository

import kotlinx.coroutines.delay

sealed class AuthResult {
    data class Success(val displayName: String) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

object AuthRepository {
    // Demo: credenciales válidas (cámbialas cuando conectes tu backend)
    private val users = mapOf(
        "leonel" to "123456",
        "maria" to "maria123",
        "lukas" to "lukas123",
        "admin" to "admin"
    )

    // Simula latencia de red
    suspend fun login(username: String, password: String): AuthResult {
        delay(650)
        if (username.isBlank() || password.isBlank()) {
            return AuthResult.Error("Completa usuario y contraseña.")
        }
        val ok = users[username.lowercase()] == password
        return if (ok) AuthResult.Success(displayName = username)
        else AuthResult.Error("Credenciales inválidas.")
    }
}