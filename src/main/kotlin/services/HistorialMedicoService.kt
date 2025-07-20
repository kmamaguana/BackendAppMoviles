package com.example.services

import com.example.domain.repository.HistorialMedicoRepository
import com.example.dto.HistorialMedicoDTO
import com.example.dto.HistorialMedicoCreateUpdateDTO
import com.example.repository.impl.HistorialMedicoRepositoryImpl

class HistorialMedicoService(
    private val repository: HistorialMedicoRepository = HistorialMedicoRepositoryImpl()
) {
    fun obtenerTodos(): List<HistorialMedicoDTO> = repository.findAll()
    fun obtenerPorId(id: String): HistorialMedicoDTO? = repository.findById(id)
    fun crear(dto: HistorialMedicoCreateUpdateDTO): HistorialMedicoDTO = repository.save(dto)
    fun actualizar(id: String, dto: HistorialMedicoCreateUpdateDTO): HistorialMedicoDTO? = repository.update(id, dto)
    fun eliminar(id: String): Boolean = repository.deleteById(id)
} 