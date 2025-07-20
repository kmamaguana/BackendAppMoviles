package com.example.repository.impl

import com.example.db.Servicios
import com.example.domain.repository.ServicioRepository
import com.example.dto.ServicioDTO
import com.example.dto.ServicioCreateUpdateDTO
import com.example.mappers.toServicioDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ServicioRepositoryImpl : ServicioRepository {
    override fun findAll(): List<ServicioDTO> = transaction {
        Servicios.selectAll().map { it.toServicioDTO() }
    }

    override fun findById(id: String): ServicioDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        Servicios.select { Servicios.id eq intId }
            .map { it.toServicioDTO() }
            .singleOrNull()
    }

    override fun save(dto: ServicioCreateUpdateDTO): ServicioDTO = transaction {
        val id = Servicios.insertAndGetId {
            it[nombre] = dto.nombre
            it[tipo] = dto.tipo
            it[descripcion] = dto.descripcion
            it[precio] = java.math.BigDecimal.valueOf(dto.precio)
        }
        findById(id.value.toString())!!
    }

    override fun update(id: String, dto: ServicioCreateUpdateDTO): ServicioDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        val updatedRows = Servicios.update({ Servicios.id eq intId }) {
            it[nombre] = dto.nombre
            it[tipo] = dto.tipo
            it[descripcion] = dto.descripcion
            it[precio] = java.math.BigDecimal.valueOf(dto.precio)
        }
        if (updatedRows > 0) findById(id) else null
    }

    override fun deleteById(id: String): Boolean = transaction {
        val intId = id.toIntOrNull() ?: return@transaction false
        Servicios.deleteWhere { Servicios.id eq intId } > 0
    }
} 