package com.example.Borrador

import kotlinx.serialization.Serializable

@Serializable
data class OrdenDTO(
    val id: String,
    val clienteId: String,
    val fecha: String,
    val total: Double,
    val estado: String
)
