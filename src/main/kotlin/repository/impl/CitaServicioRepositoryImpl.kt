package com.example.repository.impl

import com.example.db.CitaServicios
import com.example.domain.repository.CitaServicioRepository
import com.example.dto.CitaServicioDTO
import com.example.dto.CitaServicioCreateUpdateDTO
import com.example.mappers.toCitaServicioDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class CitaServicioRepositoryImpl : CitaServicioRepository {
    override fun findAll(): List<CitaServicioDTO> = transaction {
        CitaServicios.selectAll().map { it.toCitaServicioDTO() }
    }

    override fun findById(id: String): CitaServicioDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        CitaServicios.select { CitaServicios.id eq intId }
            .map { it.toCitaServicioDTO() }
            .singleOrNull()
    }

    override fun save(dto: CitaServicioCreateUpdateDTO): CitaServicioDTO = transaction {
        val id = CitaServicios.insertAndGetId {
            it[citaId] = dto.citaId.toInt()
            it[servicioId] = dto.servicioId.toInt()
            it[precioServicio] = java.math.BigDecimal.valueOf(dto.precioServicio)
        }
        findById(id.value.toString())!!
    }

    override fun update(id: String, dto: CitaServicioCreateUpdateDTO): CitaServicioDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        val updatedRows = CitaServicios.update({ CitaServicios.id eq intId }) {
            it[citaId] = dto.citaId.toInt()
            it[servicioId] = dto.servicioId.toInt()
            it[precioServicio] = java.math.BigDecimal.valueOf(dto.precioServicio)
        }
        if (updatedRows > 0) findById(id) else null
    }

    override fun deleteById(id: String): Boolean = transaction {
        val intId = id.toIntOrNull() ?: return@transaction false
        CitaServicios.deleteWhere { CitaServicios.id eq intId } > 0
    }
} 