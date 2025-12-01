package com.cardioguard.evolution.models.auth

data class LoginRequest(
    val email: String,
    val password: String
)