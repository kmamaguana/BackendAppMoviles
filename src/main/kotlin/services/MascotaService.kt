package com.example.services

import com.example.domain.repository.MascotaRepository
import com.example.dto.MascotaDTO
import com.example.dto.MascotaCreateUpdateDTO
import com.example.repository.impl.MascotaRepositoryImpl

class MascotaService(
    private val repository: MascotaRepository = MascotaRepositoryImpl()
) {
    fun obtenerTodos(): List<MascotaDTO> = repository.findAll()
    fun obtenerPorId(id: String): MascotaDTO? = repository.findById(id)
    fun crear(dto: MascotaCreateUpdateDTO): MascotaDTO = repository.save(dto)
    fun actualizar(id: String, dto: MascotaCreateUpdateDTO): MascotaDTO? = repository.update(id, dto)
    fun eliminar(id: String): Boolean = repository.deleteById(id)
} 