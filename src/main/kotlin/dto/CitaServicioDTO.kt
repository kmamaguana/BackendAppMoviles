package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class CitaServicioDTO(
    val id: String,
    val citaId: String,
    val servicioId: String,
    val precioServicio: Double
) 