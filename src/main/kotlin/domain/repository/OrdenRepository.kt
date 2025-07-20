package com.example.domain.repository

import com.example.dto.OrdenDTO
import com.example.dto.OrdenCreateUpdateDTO

interface OrdenRepository {
    fun findAll(): List<OrdenDTO>
    fun findById(id: String): OrdenDTO?
    fun save(dto: OrdenCreateUpdateDTO): OrdenDTO
    fun update(id: String, dto: OrdenCreateUpdateDTO): OrdenDTO?
    fun deleteById(id: String): Boolean
} 