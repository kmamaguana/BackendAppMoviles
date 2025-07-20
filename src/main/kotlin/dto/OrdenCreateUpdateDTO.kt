package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class OrdenCreateUpdateDTO(
    val clienteId: String,
    val fecha: String,
    val total: Double,
    val estado: String
) 