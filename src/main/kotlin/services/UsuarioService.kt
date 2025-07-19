package com.example.services

import com.example.domain.repository.UsuarioRepository
import com.example.dto.UsuarioDTO
import com.example.dto.UsuarioCreateUpdateDTO
import com.example.repository.impl.UsuarioRepositoryImpl

class UsuarioService(
    private val repository: UsuarioRepository = UsuarioRepositoryImpl()
) {

    fun obtenerTodos(): List<UsuarioDTO> = repository.findAll()

    fun obtenerPorId(id: String): UsuarioDTO? = repository.findById(id)

    fun buscarPorEmail(email: String): UsuarioDTO? = repository.findByEmail(email)

    fun crear(dto: UsuarioCreateUpdateDTO, hashedPassword: String): UsuarioDTO =
        repository.save(dto, hashedPassword)

    fun actualizar(id: String, dto: UsuarioCreateUpdateDTO, hashedPassword: String?): UsuarioDTO? =
        repository.update(id, dto, hashedPassword)

    fun eliminar(id: String): Boolean = repository.deleteById(id)
}
