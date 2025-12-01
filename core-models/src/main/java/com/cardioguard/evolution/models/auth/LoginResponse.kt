package com.cardioguard.evolution.models.auth

data class LoginResponse(
    val status: String?,    // "ok" | "error"
    val message: String?,
    val user_id: Int? = null,
    val token: String? = null,
    val username: String? = null
)