package com.example.domain.repository

import com.example.dto.CitaDTO
import com.example.dto.CitaCreateUpdateDTO

interface CitaRepository {
    fun findAll(): List<CitaDTO>
    fun findById(id: String): CitaDTO?
    fun save(dto: CitaCreateUpdateDTO): CitaDTO
    fun update(id: String, dto: CitaCreateUpdateDTO): CitaDTO?
    fun deleteById(id: String): Boolean
} 