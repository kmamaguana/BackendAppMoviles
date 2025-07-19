package com.example.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDTO(
    val token: String,
    val usuarioId: String,
    val nombre: String,
    val rol: String
)