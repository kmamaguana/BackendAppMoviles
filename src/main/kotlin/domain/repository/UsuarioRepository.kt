package com.example.domain.repository

import com.example.dto.UsuarioDTO
import com.example.dto.UsuarioCreateUpdateDTO

interface UsuarioRepository {
    fun findAll(): List<UsuarioDTO>
    fun findById(id: String): UsuarioDTO?
    fun findByEmail(email: String): UsuarioDTO?
    fun save(createDto: UsuarioCreateUpdateDTO, hashedPassword: String): UsuarioDTO
    fun update(id: String, updateDto: UsuarioCreateUpdateDTO, hashedPassword: String?): UsuarioDTO?
    fun deleteById(id: String): Boolean
}
