package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class HistorialClienteCreateUpdateDTO(
    val clienteId: String,
    val accion: String,
    val descripcion: String,
    val fecha: String
) 