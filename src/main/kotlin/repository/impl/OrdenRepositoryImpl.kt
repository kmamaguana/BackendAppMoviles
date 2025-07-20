package com.example.repository.impl

import com.example.db.Ordenes
import com.example.domain.repository.OrdenRepository
import com.example.dto.OrdenDTO
import com.example.dto.OrdenCreateUpdateDTO
import com.example.mappers.toOrdenDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class OrdenRepositoryImpl : OrdenRepository {
    override fun findAll(): List<OrdenDTO> = transaction {
        Ordenes.selectAll().map { it.toOrdenDTO() }
    }

    override fun findById(id: String): OrdenDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        Ordenes.select { Ordenes.id eq intId }
            .map { it.toOrdenDTO() }
            .singleOrNull()
    }

    override fun save(dto: OrdenCreateUpdateDTO): OrdenDTO = transaction {
        val id = Ordenes.insertAndGetId {
            it[clienteId] = dto.clienteId.toInt()
            it[fecha] = java.time.LocalDateTime.parse(dto.fecha)
            it[total] = java.math.BigDecimal.valueOf(dto.total)
            it[estado] = dto.estado
        }
        findById(id.value.toString())!!
    }

    override fun update(id: String, dto: OrdenCreateUpdateDTO): OrdenDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        val updatedRows = Ordenes.update({ Ordenes.id eq intId }) {
            it[clienteId] = dto.clienteId.toInt()
            it[fecha] = java.time.LocalDateTime.parse(dto.fecha)
            it[total] = java.math.BigDecimal.valueOf(dto.total)
            it[estado] = dto.estado
        }
        if (updatedRows > 0) findById(id) else null
    }

    override fun deleteById(id: String): Boolean = transaction {
        val intId = id.toIntOrNull() ?: return@transaction false
        Ordenes.deleteWhere { Ordenes.id eq intId } > 0
    }
} 