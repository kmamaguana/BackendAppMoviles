package com.example.repository.impl

import com.example.db.Carrito
import com.example.domain.repository.CarritoRepository
import com.example.dto.CarritoDTO
import com.example.dto.CarritoCreateUpdateDTO
import com.example.mappers.toCarritoDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class CarritoRepositoryImpl : CarritoRepository {
    override fun findAll(): List<CarritoDTO> = transaction {
        Carrito.selectAll().map { it.toCarritoDTO() }
    }

    override fun findById(id: String): CarritoDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        Carrito.select { Carrito.id eq intId }
            .map { it.toCarritoDTO() }
            .singleOrNull()
    }

    override fun save(dto: CarritoCreateUpdateDTO): CarritoDTO = transaction {
        val id = Carrito.insertAndGetId {
            it[clienteId] = dto.clienteId.toInt()
            it[productoId] = dto.productoId.toInt()
            it[cantidad] = dto.cantidad
        }
        findById(id.value.toString())!!
    }

    override fun update(id: String, dto: CarritoCreateUpdateDTO): CarritoDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        val updatedRows = Carrito.update({ Carrito.id eq intId }) {
            it[clienteId] = dto.clienteId.toInt()
            it[productoId] = dto.productoId.toInt()
            it[cantidad] = dto.cantidad
        }
        if (updatedRows > 0) findById(id) else null
    }

    override fun deleteById(id: String): Boolean = transaction {
        val intId = id.toIntOrNull() ?: return@transaction false
        Carrito.deleteWhere { Carrito.id eq intId } > 0
    }
} 