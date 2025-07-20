package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class ClienteDTO(
    val id: String,
    val usuarioId: String,
    val telefono: String,
    val direccion: String
) 