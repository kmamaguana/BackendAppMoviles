package com.example.repository.impl

import com.example.db.Usuarios
import com.example.domain.repository.UsuarioRepository
import com.example.dto.UsuarioDTO
import com.example.dto.UsuarioCreateUpdateDTO
import com.example.mappers.toUsuarioDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.time.LocalDateTime

class UsuarioRepositoryImpl : UsuarioRepository {

    // Obtiene todos los usuarios
    override fun findAll(): List<UsuarioDTO> = transaction {
        Usuarios.selectAll().map { it.toUsuarioDTO() }
    }

    // Busca un usuario por ID (ahora usando ID Int)
    override fun findById(id: String): UsuarioDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        Usuarios.select { Usuarios.id eq intId }
            .map { it.toUsuarioDTO() }
            .singleOrNull()
    }

    // Busca un usuario por email
    override fun findByEmail(email: String): UsuarioDTO? = transaction {
        Usuarios.select { Usuarios.email eq email }
            .map { it.toUsuarioDTO() }
            .singleOrNull()
    }

    // Guarda un nuevo usuario
    override fun save(createDto: UsuarioCreateUpdateDTO, hashedPassword: String): UsuarioDTO = transaction {
        val id = Usuarios.insertAndGetId {
            it[nombre] = createDto.nombre
            it[apellido] = createDto.apellido
            it[email] = createDto.email
            it[rol] = createDto.rol
            it[estado] = createDto.estado
            it[fechaNacimiento] = LocalDate.parse(createDto.fechaNacimiento)
            it[creadoEn] = LocalDateTime.now()
            it[contraseña] = hashedPassword
            it[ultimoLogin] = null
        }
        findById(id.value.toString())!!  // Convertimos Int a String para mantener la interfaz
    }

    // Actualiza datos de un usuario existente
    override fun update(id: String, updateDto: UsuarioCreateUpdateDTO, hashedPassword: String?): UsuarioDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        val updatedRows = Usuarios.update({ Usuarios.id eq intId }) {
            it[nombre] = updateDto.nombre
            it[apellido] = updateDto.apellido
            it[email] = updateDto.email
            it[rol] = updateDto.rol
            it[estado] = updateDto.estado
            it[fechaNacimiento] = LocalDate.parse(updateDto.fechaNacimiento)
            if (hashedPassword != null) {
                it[contraseña] = hashedPassword
            }
        }
        if (updatedRows > 0) findById(id) else null
    }

    // Elimina un usuario por ID
    override fun deleteById(id: String): Boolean = transaction {
        val intId = id.toIntOrNull() ?: return@transaction false
        Usuarios.deleteWhere { Usuarios.id eq intId } > 0
    }
}
