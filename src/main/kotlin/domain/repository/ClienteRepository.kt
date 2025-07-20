package com.example.domain.repository

import com.example.dto.ClienteDTO
import com.example.dto.ClienteCreateUpdateDTO

interface ClienteRepository {
    fun findAll(): List<ClienteDTO>
    fun findById(id: String): ClienteDTO?
    fun save(dto: ClienteCreateUpdateDTO): ClienteDTO
    fun update(id: String, dto: ClienteCreateUpdateDTO): ClienteDTO?
    fun deleteById(id: String): Boolean
} 