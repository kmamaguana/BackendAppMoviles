package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class ServicioCreateUpdateDTO(
    val nombre: String,
    val tipo: String,
    val descripcion: String,
    val precio: Double
) 