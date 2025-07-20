package com.example.services

import com.example.domain.repository.CitaRepository
import com.example.dto.CitaDTO
import com.example.dto.CitaCreateUpdateDTO
import com.example.repository.impl.CitaRepositoryImpl

class CitaService(
    private val repository: CitaRepository = CitaRepositoryImpl()
) {
    fun obtenerTodos(): List<CitaDTO> = repository.findAll()
    fun obtenerPorId(id: String): CitaDTO? = repository.findById(id)
    fun crear(dto: CitaCreateUpdateDTO): CitaDTO = repository.save(dto)
    fun actualizar(id: String, dto: CitaCreateUpdateDTO): CitaDTO? = repository.update(id, dto)
    fun eliminar(id: String): Boolean = repository.deleteById(id)
} 