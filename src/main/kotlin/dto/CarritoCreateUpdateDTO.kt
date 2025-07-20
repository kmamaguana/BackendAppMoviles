package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class CarritoCreateUpdateDTO(
    val clienteId: String,
    val productoId: String,
    val cantidad: Int
) 