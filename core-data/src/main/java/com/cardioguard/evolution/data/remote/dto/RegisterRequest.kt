package com.cardioguard.evolution.data.remote.dto

data class RegisterRequest(
    val nombres: String,
    val apellidos: String,
    val email: String,
    val password: String,
    val fecha_nacimiento: String? = "",
    val genero: String? = ""
)