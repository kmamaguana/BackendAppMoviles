package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class CitaDTO(
    val id: String,
    val mascotaId: String,
    val fecha: String,
    val estado: String,
    val notas: String?,
    val creadoEn: String
) 