package com.example.domain.repository

import com.example.dto.OrdenDetalleDTO
import com.example.dto.OrdenDetalleCreateUpdateDTO

interface OrdenDetalleRepository {
    fun findAll(): List<OrdenDetalleDTO>
    fun findById(id: String): OrdenDetalleDTO?
    fun save(dto: OrdenDetalleCreateUpdateDTO): OrdenDetalleDTO
    fun update(id: String, dto: OrdenDetalleCreateUpdateDTO): OrdenDetalleDTO?
    fun deleteById(id: String): Boolean
} 