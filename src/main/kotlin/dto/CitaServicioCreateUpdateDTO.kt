package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class CitaServicioCreateUpdateDTO(
    val citaId: String,
    val servicioId: String,
    val precioServicio: Double
) 