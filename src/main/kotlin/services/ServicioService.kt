package com.example.services

import com.example.domain.repository.ServicioRepository
import com.example.dto.ServicioDTO
import com.example.dto.ServicioCreateUpdateDTO
import com.example.repository.impl.ServicioRepositoryImpl

class ServicioService(
    private val repository: ServicioRepository = ServicioRepositoryImpl()
) {
    fun obtenerTodos(): List<ServicioDTO> = repository.findAll()
    fun obtenerPorId(id: String): ServicioDTO? = repository.findById(id)
    fun crear(dto: ServicioCreateUpdateDTO): ServicioDTO = repository.save(dto)
    fun actualizar(id: String, dto: ServicioCreateUpdateDTO): ServicioDTO? = repository.update(id, dto)
    fun eliminar(id: String): Boolean = repository.deleteById(id)
} 