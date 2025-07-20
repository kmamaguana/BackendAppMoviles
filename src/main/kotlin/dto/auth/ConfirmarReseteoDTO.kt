package com.example.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmarReseteoDTO(
    val nuevaContrasena: String
) 