package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioCreateUpdateDTO(
    val nombre: String,
    val apellido: String,
    val email: String,
    val password: String,
    val rol: String = "CLIENTE",
    val estado: String = "ACTIVO",
    val fechaNacimiento: String
)