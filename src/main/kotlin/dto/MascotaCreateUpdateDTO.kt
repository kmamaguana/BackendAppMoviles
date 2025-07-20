package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class MascotaCreateUpdateDTO(
    val clienteId: String,
    val nombre: String,
    val especie: String,
    val raza: String,
    val fechaNacimiento: String,
    val edad: Int,
    val peso: Double,
    val sexo: String,
    val esterilizado: Boolean,
    val vacunasAlDia: Boolean,
    val observaciones: String?,
    val estado: String,
    val creadoEn: String
) 