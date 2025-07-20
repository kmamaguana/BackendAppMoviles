package com.example.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class SolicitarReseteoDTO(
    val email: String
) 