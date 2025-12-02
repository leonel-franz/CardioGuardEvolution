package com.cardioguard.evolution.data.remote.dto

data class RegisterResponse(
    val status: String?,       // "ok" o "error"
    val message: String?       // Mensaje de error si aplica
)