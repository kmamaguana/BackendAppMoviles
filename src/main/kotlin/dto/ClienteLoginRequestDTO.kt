package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class ClienteLoginRequestDTO(
    val email: String,
    val password: String
) 