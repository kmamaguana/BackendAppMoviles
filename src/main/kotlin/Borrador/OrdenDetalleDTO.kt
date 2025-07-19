package com.example.Borrador

import kotlinx.serialization.Serializable

@Serializable
data class OrdenDetalleDTO(
    val id: String,
    val ordenId: String,
    val productoId: String,
    val cantidad: Int,
    val precioUnitario: Double
)
