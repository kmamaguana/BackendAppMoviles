package com.example.services

import com.example.domain.repository.ReporteAdminRepository
import com.example.dto.ReporteAdminDTO
import com.example.repository.impl.ReporteAdminRepositoryImpl

class ReporteAdminService(
    private val repository: ReporteAdminRepository = ReporteAdminRepositoryImpl()
) {
    fun obtenerTodos(): List<ReporteAdminDTO> = repository.findAll()
    fun obtenerPorId(id: String): ReporteAdminDTO? = repository.findById(id)
    fun crear(dto: ReporteAdminDTO): ReporteAdminDTO = repository.save(dto)
    fun actualizar(id: String, dto: ReporteAdminDTO): ReporteAdminDTO? = repository.update(id, dto)
    fun eliminar(id: String): Boolean = repository.deleteById(id)
} 