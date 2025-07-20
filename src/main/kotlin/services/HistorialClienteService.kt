package com.example.services

import com.example.domain.repository.HistorialClienteRepository
import com.example.dto.HistorialClienteDTO
import com.example.dto.HistorialClienteCreateUpdateDTO
import com.example.repository.impl.HistorialClienteRepositoryImpl

class HistorialClienteService(
    private val repository: HistorialClienteRepository = HistorialClienteRepositoryImpl()
) {
    fun obtenerTodos(): List<HistorialClienteDTO> = repository.findAll()
    fun obtenerPorId(id: String): HistorialClienteDTO? = repository.findById(id)
    fun crear(dto: HistorialClienteCreateUpdateDTO): HistorialClienteDTO = repository.save(dto)
    fun actualizar(id: String, dto: HistorialClienteCreateUpdateDTO): HistorialClienteDTO? = repository.update(id, dto)
    fun eliminar(id: String): Boolean = repository.deleteById(id)
} 