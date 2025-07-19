package com.example.Borrador

import kotlinx.serialization.Serializable

@Serializable
data class HistorialClienteDTO(
    val id: String,
    val clienteId: String,
    val accion: String,
    val descripcion: String,
    val fecha: String
)
