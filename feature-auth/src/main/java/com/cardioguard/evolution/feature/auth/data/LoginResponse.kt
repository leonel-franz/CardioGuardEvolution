package com.cardioguard.evolution.feature.auth.data

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String?
)
