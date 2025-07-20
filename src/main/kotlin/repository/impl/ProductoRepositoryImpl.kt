package com.example.repository.impl

import com.example.db.Productos
import com.example.domain.repository.ProductoRepository
import com.example.dto.ProductoDTO
import com.example.dto.ProductoCreateUpdateDTO
import com.example.mappers.toProductoDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ProductoRepositoryImpl : ProductoRepository {
    override fun findAll(): List<ProductoDTO> = transaction {
        Productos.selectAll().map { it.toProductoDTO() }
    }

    override fun findById(id: String): ProductoDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        Productos.select { Productos.id eq intId }
            .map { it.toProductoDTO() }
            .singleOrNull()
    }

    override fun save(dto: ProductoCreateUpdateDTO): ProductoDTO = transaction {
        val id = Productos.insertAndGetId {
            it[nombre] = dto.nombre
            it[descripcion] = dto.descripcion
            it[precio] = java.math.BigDecimal.valueOf(dto.precio)
            it[stock] = dto.stock
            it[imagenUrl] = dto.imagenUrl
            it[categoriaId] = dto.categoriaId.toInt()
        }
        findById(id.value.toString())!!
    }

    override fun update(id: String, dto: ProductoCreateUpdateDTO): ProductoDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        val updatedRows = Productos.update({ Productos.id eq intId }) {
            it[nombre] = dto.nombre
            it[descripcion] = dto.descripcion
            it[precio] = java.math.BigDecimal.valueOf(dto.precio)
            it[stock] = dto.stock
            it[imagenUrl] = dto.imagenUrl
            it[categoriaId] = dto.categoriaId.toInt()
        }
        if (updatedRows > 0) findById(id) else null
    }

    override fun deleteById(id: String): Boolean = transaction {
        val intId = id.toIntOrNull() ?: return@transaction false
        Productos.deleteWhere { Productos.id eq intId } > 0
    }
} 