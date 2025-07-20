package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class ClienteCreateUpdateDTO(
    val usuarioId: String,
    val telefono: String,
    val direccion: String
) 