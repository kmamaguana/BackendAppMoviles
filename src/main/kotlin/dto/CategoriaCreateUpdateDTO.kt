package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoriaCreateUpdateDTO(
    val nombre: String,
    val descripcion: String?
) 