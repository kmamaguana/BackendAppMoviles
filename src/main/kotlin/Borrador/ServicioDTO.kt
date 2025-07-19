package com.example.Borrador

import kotlinx.serialization.Serializable

@Serializable
data class ServicioDTO(
    val id: String,
    val nombre: String,
    val tipo: String,
    val descripcion: String,
    val precio: Double
)
