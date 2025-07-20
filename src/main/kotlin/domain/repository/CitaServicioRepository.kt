package com.example.domain.repository

import com.example.dto.CitaServicioDTO
import com.example.dto.CitaServicioCreateUpdateDTO

interface CitaServicioRepository {
    fun findAll(): List<CitaServicioDTO>
    fun findById(id: String): CitaServicioDTO?
    fun save(dto: CitaServicioCreateUpdateDTO): CitaServicioDTO
    fun update(id: String, dto: CitaServicioCreateUpdateDTO): CitaServicioDTO?
    fun deleteById(id: String): Boolean
} 