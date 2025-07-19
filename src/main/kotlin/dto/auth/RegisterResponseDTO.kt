package com.example.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponseDTO(
    val usuarioId: String,
    val token: String,
    val mensaje: String = "Usuario registrado correctamente"
)
