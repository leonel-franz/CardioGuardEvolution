package com.cardioguard.evolution.data.repository

import com.cardioguard.evolution.common.datastore.DataStoreManager
import com.cardioguard.evolution.data.remote.ApiService
import com.cardioguard.evolution.models.auth.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

sealed class AuthResult {
    data class Success(val username: String, val token: String) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

@Singleton
class AuthRepository @Inject constructor(
    private val api: ApiService,
    private val ds: DataStoreManager
) {

    suspend fun login(email: String, password: String): AuthResult {
        return withContext(Dispatchers.IO) {
            try {
                val resp = api.login(LoginRequest(email = email, password = password))

                val token = resp.token
                val username = resp.username ?: email

                if (resp.status == "ok" && !token.isNullOrEmpty()) {
                    // Guardar token y username en DataStore
                    ds.saveToken(token)
                    ds.saveUser(username)
                    AuthResult.Success(username, token)
                } else {
                    AuthResult.Error(resp.message ?: "Error en login")
                }
            } catch (e: Exception) {
                AuthResult.Error(e.message ?: "Fallo conexión")
            }
        }
    }

    suspend fun register(
        nombres: String,
        apellidos: String,
        email: String,
        password: String,
        fecha: String = "",
        genero: String = ""
    ): AuthResult {
        return withContext(Dispatchers.IO) {
            try {
                val resp = api.register(
                    com.cardioguard.evolution.models.auth.RegisterRequest(
                        nombres = nombres,
                        apellidos = apellidos,
                        email = email,
                        password = password,
                        fecha_nacimiento = fecha,
                        genero = genero
                    )
                )

                if (resp.status == "ok") {
                    // Registrado exitosamente
                    AuthResult.Success(email, "")
                } else {
                    AuthResult.Error(resp.message ?: "Error en registro")
                }
            } catch (e: Exception) {
                AuthResult.Error(e.message ?: "Fallo conexión")
            }
        }
    }

    fun getTokenFlow() = ds.authToken
}
