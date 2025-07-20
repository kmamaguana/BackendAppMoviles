package com.example.services

import com.example.domain.repository.ClienteRepository
import com.example.dto.ClienteDTO
import com.example.dto.ClienteCreateUpdateDTO
import com.example.repository.impl.ClienteRepositoryImpl

class ClienteService(
    private val repository: ClienteRepository = ClienteRepositoryImpl()
) {
    fun obtenerTodos(): List<ClienteDTO> = repository.findAll()
    fun obtenerPorId(id: String): ClienteDTO? = repository.findById(id)
    fun crear(dto: ClienteCreateUpdateDTO): ClienteDTO = repository.save(dto)
    fun actualizar(id: String, dto: ClienteCreateUpdateDTO): ClienteDTO? = repository.update(id, dto)
    fun eliminar(id: String): Boolean = repository.deleteById(id)
} 