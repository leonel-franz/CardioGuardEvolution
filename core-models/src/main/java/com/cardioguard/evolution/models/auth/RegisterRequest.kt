package com.cardioguard.evolution.models.auth

data class RegisterRequest(
    val nombres: String,
    val apellidos: String,
    val email: String,
    val password: String,
    val fecha_nacimiento: String = "",
    val genero: String = ""
)