package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class ClienteDTO(
    val id: String,
    val usuarioId: String,
    val nombre: String, // Nuevo campo para el nombre del usuario
    val email: String, // Email del usuario
    val fechaNacimiento: String, // Fecha de nacimiento del usuario
    val telefono: String,
    val direccion: String,
    val activo: Boolean = true // Campo para soft delete, por defecto true
) 