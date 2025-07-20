package com.example.domain.repository

import com.example.dto.ReporteAdminDTO

interface ReporteAdminRepository {
    fun findAll(): List<ReporteAdminDTO>
    fun findById(id: String): ReporteAdminDTO?
    fun save(dto: ReporteAdminDTO): ReporteAdminDTO
    fun update(id: String, dto: ReporteAdminDTO): ReporteAdminDTO?
    fun deleteById(id: String): Boolean
} 