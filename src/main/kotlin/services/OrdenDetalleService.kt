package com.example.services

import com.example.domain.repository.OrdenDetalleRepository
import com.example.dto.OrdenDetalleDTO
import com.example.dto.OrdenDetalleCreateUpdateDTO
import com.example.repository.impl.OrdenDetalleRepositoryImpl

class OrdenDetalleService(
    private val repository: OrdenDetalleRepository = OrdenDetalleRepositoryImpl()
) {
    fun obtenerTodos(): List<OrdenDetalleDTO> = repository.findAll()
    fun obtenerPorId(id: String): OrdenDetalleDTO? = repository.findById(id)
    fun crear(dto: OrdenDetalleCreateUpdateDTO): OrdenDetalleDTO = repository.save(dto)
    fun actualizar(id: String, dto: OrdenDetalleCreateUpdateDTO): OrdenDetalleDTO? = repository.update(id, dto)
    fun eliminar(id: String): Boolean = repository.deleteById(id)
} 