package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class NivelFidelidadDTO(
    val id: String,
    val citasMinimas: Int,
    val porcentajeDescuento: Double,
    val descripcion: String?
) 