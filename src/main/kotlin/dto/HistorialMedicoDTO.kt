package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class HistorialMedicoDTO(
    val id: String,
    val mascotaId: String,
    val fecha: String,
    val tipo: String,
    val descripcion: String,
    val veterinario: String
) 