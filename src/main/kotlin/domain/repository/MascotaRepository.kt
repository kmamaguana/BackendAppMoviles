package com.example.domain.repository

import com.example.dto.MascotaDTO
import com.example.dto.MascotaCreateUpdateDTO

interface MascotaRepository {
    fun findAll(): List<MascotaDTO>
    fun findById(id: String): MascotaDTO?
    fun save(dto: MascotaCreateUpdateDTO): MascotaDTO
    fun update(id: String, dto: MascotaCreateUpdateDTO): MascotaDTO?
    fun deleteById(id: String): Boolean
} 