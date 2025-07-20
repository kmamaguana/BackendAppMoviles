package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductoCreateUpdateDTO(
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val imagenUrl: String?,
    val categoriaId: String
) 