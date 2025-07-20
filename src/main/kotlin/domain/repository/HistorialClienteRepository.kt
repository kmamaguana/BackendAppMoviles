package com.example.domain.repository

import com.example.dto.HistorialClienteDTO
import com.example.dto.HistorialClienteCreateUpdateDTO

interface HistorialClienteRepository {
    fun findAll(): List<HistorialClienteDTO>
    fun findById(id: String): HistorialClienteDTO?
    fun save(dto: HistorialClienteCreateUpdateDTO): HistorialClienteDTO
    fun update(id: String, dto: HistorialClienteCreateUpdateDTO): HistorialClienteDTO?
    fun deleteById(id: String): Boolean
} 