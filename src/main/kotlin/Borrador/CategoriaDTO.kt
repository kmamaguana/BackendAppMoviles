package com.example.Borrador

import kotlinx.serialization.Serializable

@Serializable
data class CategoriaDTO(
    val id: String,
    val nombre: String,
    val descripcion: String?
)
