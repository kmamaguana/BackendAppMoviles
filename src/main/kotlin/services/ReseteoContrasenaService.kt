package com.example.services

import com.example.domain.repository.ReseteoContrasenaRepository
import com.example.dto.ReseteoContrasenaDTO
import com.example.repository.impl.ReseteoContrasenaRepositoryImpl

class ReseteoContrasenaService(
    private val repository: ReseteoContrasenaRepository = ReseteoContrasenaRepositoryImpl()
) {
    fun obtenerTodos(): List<ReseteoContrasenaDTO> = repository.findAll()
    fun obtenerPorId(id: String): ReseteoContrasenaDTO? = repository.findById(id)
    fun obtenerPorToken(token: String): ReseteoContrasenaDTO? = repository.findByToken(token)
    fun crear(dto: ReseteoContrasenaDTO): ReseteoContrasenaDTO = repository.save(dto)
    fun actualizar(id: String, dto: ReseteoContrasenaDTO): ReseteoContrasenaDTO? = repository.update(id, dto)
    fun eliminar(id: String): Boolean = repository.deleteById(id)
} 