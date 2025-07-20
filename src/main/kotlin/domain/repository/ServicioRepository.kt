package com.example.domain.repository

import com.example.dto.ServicioDTO
import com.example.dto.ServicioCreateUpdateDTO

interface ServicioRepository {
    fun findAll(): List<ServicioDTO>
    fun findById(id: String): ServicioDTO?
    fun save(dto: ServicioCreateUpdateDTO): ServicioDTO
    fun update(id: String, dto: ServicioCreateUpdateDTO): ServicioDTO?
    fun deleteById(id: String): Boolean
} 