package com.example.services

import com.example.domain.repository.CategoriaRepository
import com.example.dto.CategoriaDTO
import com.example.dto.CategoriaCreateUpdateDTO
import com.example.repository.impl.CategoriaRepositoryImpl

class CategoriaService(
    private val repository: CategoriaRepository = CategoriaRepositoryImpl()
) {
    fun obtenerTodos(): List<CategoriaDTO> = repository.findAll()
    fun obtenerPorId(id: String): CategoriaDTO? = repository.findById(id)
    fun crear(dto: CategoriaCreateUpdateDTO): CategoriaDTO = repository.save(dto)
    fun actualizar(id: String, dto: CategoriaCreateUpdateDTO): CategoriaDTO? = repository.update(id, dto)
    fun eliminar(id: String): Boolean = repository.deleteById(id)
} 