package com.example.Borrador

import kotlinx.serialization.Serializable

@Serializable
data class CarritoDTO(
    val id: String,
    val clienteId: String,
    val productoId: String,
    val cantidad: Int
)
