package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReseteoContrasenaDTO(
    val id: String,
    val usuarioId: String,
    val token: String,
    val expiracion: String,
    val usado: Boolean
) 