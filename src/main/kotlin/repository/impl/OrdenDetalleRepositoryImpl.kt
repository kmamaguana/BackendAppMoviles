package com.example.repository.impl

import com.example.db.OrdenDetalles
import com.example.domain.repository.OrdenDetalleRepository
import com.example.dto.OrdenDetalleDTO
import com.example.dto.OrdenDetalleCreateUpdateDTO
import com.example.mappers.toOrdenDetalleDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class OrdenDetalleRepositoryImpl : OrdenDetalleRepository {
    override fun findAll(): List<OrdenDetalleDTO> = transaction {
        OrdenDetalles.selectAll().map { it.toOrdenDetalleDTO() }
    }

    override fun findById(id: String): OrdenDetalleDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        OrdenDetalles.select { OrdenDetalles.id eq intId }
            .map { it.toOrdenDetalleDTO() }
            .singleOrNull()
    }

    override fun save(dto: OrdenDetalleCreateUpdateDTO): OrdenDetalleDTO = transaction {
        val id = OrdenDetalles.insertAndGetId {
            it[ordenId] = dto.ordenId.toInt()
            it[productoId] = dto.productoId.toInt()
            it[cantidad] = dto.cantidad
            it[precioUnitario] = java.math.BigDecimal.valueOf(dto.precioUnitario)
        }
        findById(id.value.toString())!!
    }

    override fun update(id: String, dto: OrdenDetalleCreateUpdateDTO): OrdenDetalleDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        val updatedRows = OrdenDetalles.update({ OrdenDetalles.id eq intId }) {
            it[ordenId] = dto.ordenId.toInt()
            it[productoId] = dto.productoId.toInt()
            it[cantidad] = dto.cantidad
            it[precioUnitario] = java.math.BigDecimal.valueOf(dto.precioUnitario)
        }
        if (updatedRows > 0) findById(id) else null
    }

    override fun deleteById(id: String): Boolean = transaction {
        val intId = id.toIntOrNull() ?: return@transaction false
        OrdenDetalles.deleteWhere { OrdenDetalles.id eq intId } > 0
    }
} 