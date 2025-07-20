package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class NivelFidelidadCreateUpdateDTO(
    val citasMinimas: Int,
    val porcentajeDescuento: Double,
    val descripcion: String?
) 