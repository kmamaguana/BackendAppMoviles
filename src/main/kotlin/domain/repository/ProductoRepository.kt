package com.example.domain.repository

import com.example.dto.ProductoDTO
import com.example.dto.ProductoCreateUpdateDTO

interface ProductoRepository {
    fun findAll(): List<ProductoDTO>
    fun findById(id: String): ProductoDTO?
    fun save(dto: ProductoCreateUpdateDTO): ProductoDTO
    fun update(id: String, dto: ProductoCreateUpdateDTO): ProductoDTO?
    fun deleteById(id: String): Boolean
} 