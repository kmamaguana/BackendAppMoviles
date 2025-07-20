package com.example.repository.impl

import com.example.db.HistorialCliente
import com.example.domain.repository.HistorialClienteRepository
import com.example.dto.HistorialClienteDTO
import com.example.dto.HistorialClienteCreateUpdateDTO
import com.example.mappers.toHistorialClienteDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class HistorialClienteRepositoryImpl : HistorialClienteRepository {
    override fun findAll(): List<HistorialClienteDTO> = transaction {
        HistorialCliente.selectAll().map { it.toHistorialClienteDTO() }
    }

    override fun findById(id: String): HistorialClienteDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        HistorialCliente.select { HistorialCliente.id eq intId }
            .map { it.toHistorialClienteDTO() }
            .singleOrNull()
    }

    override fun save(dto: HistorialClienteCreateUpdateDTO): HistorialClienteDTO = transaction {
        val id = HistorialCliente.insertAndGetId {
            it[clienteId] = dto.clienteId.toInt()
            it[accion] = dto.accion
            it[descripcion] = dto.descripcion
            it[fecha] = java.time.LocalDateTime.parse(dto.fecha)
        }
        findById(id.value.toString())!!
    }

    override fun update(id: String, dto: HistorialClienteCreateUpdateDTO): HistorialClienteDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        val updatedRows = HistorialCliente.update({ HistorialCliente.id eq intId }) {
            it[clienteId] = dto.clienteId.toInt()
            it[accion] = dto.accion
            it[descripcion] = dto.descripcion
            it[fecha] = java.time.LocalDateTime.parse(dto.fecha)
        }
        if (updatedRows > 0) findById(id) else null
    }

    override fun deleteById(id: String): Boolean = transaction {
        val intId = id.toIntOrNull() ?: return@transaction false
        HistorialCliente.deleteWhere { HistorialCliente.id eq intId } > 0
    }
} 