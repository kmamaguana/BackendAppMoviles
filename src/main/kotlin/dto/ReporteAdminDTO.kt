package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReporteAdminDTO(
    val id: String,
    val fecha: String,
    val tipo: String,
    val contenido: String,
    val generadoPor: String
) 