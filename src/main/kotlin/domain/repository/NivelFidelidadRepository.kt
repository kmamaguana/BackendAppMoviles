package com.example.domain.repository

import com.example.dto.NivelFidelidadDTO
import com.example.dto.NivelFidelidadCreateUpdateDTO

interface NivelFidelidadRepository {
    fun findAll(): List<NivelFidelidadDTO>
    fun findById(id: String): NivelFidelidadDTO?
    fun save(dto: NivelFidelidadCreateUpdateDTO): NivelFidelidadDTO
    fun update(id: String, dto: NivelFidelidadCreateUpdateDTO): NivelFidelidadDTO?
    fun deleteById(id: String): Boolean
} 