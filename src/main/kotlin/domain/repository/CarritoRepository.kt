package com.example.domain.repository

import com.example.dto.CarritoDTO
import com.example.dto.CarritoCreateUpdateDTO

interface CarritoRepository {
    fun findAll(): List<CarritoDTO>
    fun findById(id: String): CarritoDTO?
    fun save(dto: CarritoCreateUpdateDTO): CarritoDTO
    fun update(id: String, dto: CarritoCreateUpdateDTO): CarritoDTO?
    fun deleteById(id: String): Boolean
} 