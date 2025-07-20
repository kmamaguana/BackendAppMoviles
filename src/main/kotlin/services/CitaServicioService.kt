package com.example.services

import com.example.domain.repository.CitaServicioRepository
import com.example.dto.CitaServicioDTO
import com.example.dto.CitaServicioCreateUpdateDTO
import com.example.repository.impl.CitaServicioRepositoryImpl

class CitaServicioService(
    private val repository: CitaServicioRepository = CitaServicioRepositoryImpl()
) {
    fun obtenerTodos(): List<CitaServicioDTO> = repository.findAll()
    fun obtenerPorId(id: String): CitaServicioDTO? = repository.findById(id)
    fun crear(dto: CitaServicioCreateUpdateDTO): CitaServicioDTO = repository.save(dto)
    fun actualizar(id: String, dto: CitaServicioCreateUpdateDTO): CitaServicioDTO? = repository.update(id, dto)
    fun eliminar(id: String): Boolean = repository.deleteById(id)
} 