package com.example.services

import com.example.domain.repository.CarritoRepository
import com.example.dto.CarritoDTO
import com.example.dto.CarritoCreateUpdateDTO
import com.example.repository.impl.CarritoRepositoryImpl

class CarritoService(
    private val repository: CarritoRepository = CarritoRepositoryImpl()
) {
    fun obtenerTodos(): List<CarritoDTO> = repository.findAll()
    fun obtenerPorId(id: String): CarritoDTO? = repository.findById(id)
    fun crear(dto: CarritoCreateUpdateDTO): CarritoDTO = repository.save(dto)
    fun actualizar(id: String, dto: CarritoCreateUpdateDTO): CarritoDTO? = repository.update(id, dto)
    fun eliminar(id: String): Boolean = repository.deleteById(id)
} 