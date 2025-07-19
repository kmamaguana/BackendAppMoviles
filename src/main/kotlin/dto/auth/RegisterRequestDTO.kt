package com.example.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDTO(
    val nombre: String,
    val apellido: String,
    val email: String,
    val password: String,
    val rol: String = "CLIENTE",
    val fechaNacimiento: String,
    val telefono: String,
    val direccion: String
)
