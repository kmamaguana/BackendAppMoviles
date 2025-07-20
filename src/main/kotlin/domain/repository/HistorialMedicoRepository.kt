package com.example.domain.repository

import com.example.dto.HistorialMedicoDTO
import com.example.dto.HistorialMedicoCreateUpdateDTO

interface HistorialMedicoRepository {
    fun findAll(): List<HistorialMedicoDTO>
    fun findById(id: String): HistorialMedicoDTO?
    fun save(dto: HistorialMedicoCreateUpdateDTO): HistorialMedicoDTO
    fun update(id: String, dto: HistorialMedicoCreateUpdateDTO): HistorialMedicoDTO?
    fun deleteById(id: String): Boolean
} 