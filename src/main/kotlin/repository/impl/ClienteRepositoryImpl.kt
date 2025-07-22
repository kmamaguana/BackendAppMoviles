package com.example.repository.impl

import com.example.db.Clientes
import com.example.db.Usuarios
import com.example.domain.repository.ClienteRepository
import com.example.dto.ClienteDTO
import com.example.dto.ClienteCreateUpdateDTO
import com.example.mappers.toClienteDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ClienteRepositoryImpl : ClienteRepository {
    override fun findAll(): List<ClienteDTO> = transaction {
        (Clientes innerJoin Usuarios).selectAll().map {
            val nombreUsuario = it[Usuarios.nombre]
            it.toClienteDTO(nombreUsuario)
        }
    }

    override fun findById(id: String): ClienteDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        (Clientes innerJoin Usuarios).select { Clientes.id eq intId }
            .map {
                val nombreUsuario = it[Usuarios.nombre]
                it.toClienteDTO(nombreUsuario)
            }
            .singleOrNull()
    }

    override fun save(dto: ClienteCreateUpdateDTO): ClienteDTO = transaction {
        val id = Clientes.insertAndGetId {
            it[usuarioId] = dto.usuarioId.toInt()
            it[telefono] = dto.telefono
            it[direccion] = dto.direccion
        }
        findById(id.value.toString())!!
    }

    override fun update(id: String, dto: ClienteCreateUpdateDTO): ClienteDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        val updatedRows = Clientes.update({ Clientes.id eq intId }) {
            it[usuarioId] = dto.usuarioId.toInt()
            it[telefono] = dto.telefono
            it[direccion] = dto.direccion
        }
        if (updatedRows > 0) findById(id) else null
    }

    override fun deleteById(id: String): Boolean = transaction {
        val intId = id.toIntOrNull() ?: return@transaction false
        Clientes.deleteWhere { Clientes.id eq intId } > 0
    }
} 