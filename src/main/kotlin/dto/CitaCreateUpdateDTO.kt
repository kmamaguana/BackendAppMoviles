package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class CitaCreateUpdateDTO(
    val mascotaId: String,
    val fecha: String,
    val estado: String,
    val notas: String?,
    val creadoEn: String
) 