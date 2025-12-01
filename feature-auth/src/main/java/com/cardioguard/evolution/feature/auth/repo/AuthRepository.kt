package com.cardioguard.evolution.feature.auth.repo

import com.cardioguard.evolution.feature.auth.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

sealed class AuthResult {
    data class Success(val username: String, val userId: String?) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

@Singleton
class AuthRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun login(email: String, password: String): AuthResult = withContext(Dispatchers.IO) {
        try {
            val resp = api.login(email, password)
            if (resp.isSuccessful) {
                val body = resp.body()
                if (body != null && body.status == "ok") {
                    return@withContext AuthResult.Success(username = email, userId = body.user_id)
                } else {
                    return@withContext AuthResult.Error(body?.message ?: "Error en respuesta")
                }
            } else {
                val msg = resp.errorBody()?.string()
                return@withContext AuthResult.Error(msg ?: "HTTP ${resp.code()}")
            }
        } catch (e: Exception) {
            return@withContext AuthResult.Error(e.localizedMessage ?: "Error de red")
        }
    }

    suspend fun register(name: String, email: String, password: String): AuthResult = withContext(Dispatchers.IO) {
        try {
            val resp = api.register(email = email, password = password, name = name)
            if (resp.isSuccessful) {
                val body = resp.body()
                if (body != null && body.status == "ok") {
                    return@withContext AuthResult.Success(username = email, userId = null)
                } else {
                    return@withContext AuthResult.Error(body?.message ?: "Error al registrar")
                }
            } else {
                return@withContext AuthResult.Error("HTTP ${resp.code()}")
            }
        } catch (e: Exception) {
            return@withContext AuthResult.Error(e.localizedMessage ?: "Error de red")
        }
    }
}
