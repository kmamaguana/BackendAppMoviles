package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioDTO(
    val id: String,
    val nombre: String,
    val apellido: String,
    val email: String,
    val rol: String,
    val estado: String,
    val fechaNacimiento: String,
    val ultimoLogin: String?,
    val creadoEn: String
)

