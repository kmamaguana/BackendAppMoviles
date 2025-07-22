package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class ClienteDTO(
    val id: String,
    val usuarioId: String,
    val nombre: String, // Nuevo campo para el nombre del usuario
    val telefono: String,
    val direccion: String
) 