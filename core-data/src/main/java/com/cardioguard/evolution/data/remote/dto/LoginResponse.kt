package com.cardioguard.evolution.data.remote.dto

data class LoginResponse(
    val userId: String,
    val token: String,
    val name: String
)