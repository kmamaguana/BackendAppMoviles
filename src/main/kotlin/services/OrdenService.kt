package com.example.services

import com.example.domain.repository.OrdenRepository
import com.example.dto.OrdenDTO
import com.example.dto.OrdenCreateUpdateDTO
import com.example.repository.impl.OrdenRepositoryImpl

class OrdenService(
    private val repository: OrdenRepository = OrdenRepositoryImpl()
) {
    fun obtenerTodos(): List<OrdenDTO> = repository.findAll()
    fun obtenerPorId(id: String): OrdenDTO? = repository.findById(id)
    fun crear(dto: OrdenCreateUpdateDTO): OrdenDTO = repository.save(dto)
    fun actualizar(id: String, dto: OrdenCreateUpdateDTO): OrdenDTO? = repository.update(id, dto)
    fun eliminar(id: String): Boolean = repository.deleteById(id)
} 