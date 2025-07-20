package com.example.domain.repository

import com.example.dto.ReseteoContrasenaDTO

interface ReseteoContrasenaRepository {
    fun findAll(): List<ReseteoContrasenaDTO>
    fun findById(id: String): ReseteoContrasenaDTO?
    fun findByToken(token: String): ReseteoContrasenaDTO?
    fun save(dto: ReseteoContrasenaDTO): ReseteoContrasenaDTO
    fun update(id: String, dto: ReseteoContrasenaDTO): ReseteoContrasenaDTO?
    fun deleteById(id: String): Boolean
} 