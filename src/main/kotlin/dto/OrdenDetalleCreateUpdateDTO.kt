package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class OrdenDetalleCreateUpdateDTO(
    val ordenId: String,
    val productoId: String,
    val cantidad: Int,
    val precioUnitario: Double
) 