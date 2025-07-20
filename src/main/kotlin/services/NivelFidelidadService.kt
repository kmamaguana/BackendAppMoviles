package com.example.services

import com.example.domain.repository.NivelFidelidadRepository
import com.example.dto.NivelFidelidadDTO
import com.example.dto.NivelFidelidadCreateUpdateDTO
import com.example.repository.impl.NivelFidelidadRepositoryImpl

class NivelFidelidadService(
    private val repository: NivelFidelidadRepository = NivelFidelidadRepositoryImpl()
) {
    fun obtenerTodos(): List<NivelFidelidadDTO> = repository.findAll()
    fun obtenerPorId(id: String): NivelFidelidadDTO? = repository.findById(id)
    fun crear(dto: NivelFidelidadCreateUpdateDTO): NivelFidelidadDTO = repository.save(dto)
    fun actualizar(id: String, dto: NivelFidelidadCreateUpdateDTO): NivelFidelidadDTO? = repository.update(id, dto)
    fun eliminar(id: String): Boolean = repository.deleteById(id)
} 