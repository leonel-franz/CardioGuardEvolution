package com.cardioguard.evolution.data.repository

import kotlinx.coroutines.Dispatchers
import com.cardioguard.evolution.data.remote.ApiService
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

sealed class AuthResult {
    data class Success(val username: String) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

@Singleton
class AuthRepository @Inject constructor(
    private val api: ApiService
) {

    // ------------------ LOGIN ------------------
    suspend fun login(email: String, password: String): AuthResult {
        return withContext(Dispatchers.IO) {
            try {
                val resp = api.login(email, password)

                if (resp.isSuccessful) {
                    val body = resp.body()
                    if (body?.status == "ok" && body.user_id != null) {
                        AuthResult.Success(email)
                    } else {
                        AuthResult.Error(body?.message ?: "Usuario o contraseña incorrectos")
                    }
                } else {
                    AuthResult.Error("Error en la conexión: ${resp.code()}")
                }
            } catch (e: Exception) {
                AuthResult.Error(e.message ?: "Fallo de conexión")
            }
        }
    }

    // ------------------ REGISTER (ahora envía JSON) ------------------
    suspend fun register(
        nombres: String,
        apellidos: String,
        email: String,
        password: String,
        fecha_nacimiento: String = "",
        genero: String = ""
    ): AuthResult {
        return withContext(Dispatchers.IO) {
            try {

                // Construimos el JSON que espera register.php
                val params = mutableMapOf(
                    "nombres" to nombres,
                    "apellidos" to apellidos,
                    "email" to email,
                    "password" to password
                )

                if (fecha_nacimiento.isNotBlank()) params["fecha_nacimiento"] = fecha_nacimiento
                if (genero.isNotBlank()) params["genero"] = genero

                val resp = api.register(params)

                if (resp.isSuccessful) {
                    val body = resp.body()
                    if (body?.status == "ok") {
                        AuthResult.Success("$nombres $apellidos")
                    } else {
                        AuthResult.Error(body?.message ?: "Error en registro")
                    }
                } else {
                    AuthResult.Error("Error en la conexión: ${resp.code()}")
                }

            } catch (e: Exception) {
                AuthResult.Error(e.message ?: "Fallo de conexión")
            }
        }
    }
}
