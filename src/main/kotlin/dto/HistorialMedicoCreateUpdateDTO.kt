package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class HistorialMedicoCreateUpdateDTO(
    val mascotaId: String,
    val fecha: String,
    val tipo: String,
    val descripcion: String,
    val veterinario: String
) 