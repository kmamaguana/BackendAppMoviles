package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class ClienteLoginResponseDTO(
    val token: String,
    val clienteId: String,
    val nombre: String,
    val email: String
) 