package com.example.domain.repository

import com.example.dto.CategoriaDTO
import com.example.dto.CategoriaCreateUpdateDTO

interface CategoriaRepository {
    fun findAll(): List<CategoriaDTO>
    fun findById(id: String): CategoriaDTO?
    fun save(dto: CategoriaCreateUpdateDTO): CategoriaDTO
    fun update(id: String, dto: CategoriaCreateUpdateDTO): CategoriaDTO?
    fun deleteById(id: String): Boolean
} 