package com.example.services

import com.example.domain.repository.ProductoRepository
import com.example.dto.ProductoDTO
import com.example.dto.ProductoCreateUpdateDTO
import com.example.repository.impl.ProductoRepositoryImpl

class ProductoService(
    private val repository: ProductoRepository = ProductoRepositoryImpl()
) {
    fun obtenerTodos(): List<ProductoDTO> = repository.findAll()
    fun obtenerPorId(id: String): ProductoDTO? = repository.findById(id)
    fun crear(dto: ProductoCreateUpdateDTO): ProductoDTO = repository.save(dto)
    fun actualizar(id: String, dto: ProductoCreateUpdateDTO): ProductoDTO? = repository.update(id, dto)
    fun eliminar(id: String): Boolean = repository.deleteById(id)
} 