package com.example.repository.impl

import com.example.db.NivelesFidelidad
import com.example.domain.repository.NivelFidelidadRepository
import com.example.dto.NivelFidelidadDTO
import com.example.dto.NivelFidelidadCreateUpdateDTO
import com.example.mappers.toNivelFidelidadDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class NivelFidelidadRepositoryImpl : NivelFidelidadRepository {
    override fun findAll(): List<NivelFidelidadDTO> = transaction {
        NivelesFidelidad.selectAll().map { it.toNivelFidelidadDTO() }
    }

    override fun findById(id: String): NivelFidelidadDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        NivelesFidelidad.select { NivelesFidelidad.id eq intId }
            .map { it.toNivelFidelidadDTO() }
            .singleOrNull()
    }

    override fun save(dto: NivelFidelidadCreateUpdateDTO): NivelFidelidadDTO = transaction {
        val id = NivelesFidelidad.insertAndGetId {
            it[citasMinimas] = dto.citasMinimas
            it[porcentajeDescuento] = java.math.BigDecimal.valueOf(dto.porcentajeDescuento)
            it[descripcion] = dto.descripcion
        }
        findById(id.value.toString())!!
    }

    override fun update(id: String, dto: NivelFidelidadCreateUpdateDTO): NivelFidelidadDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        val updatedRows = NivelesFidelidad.update({ NivelesFidelidad.id eq intId }) {
            it[citasMinimas] = dto.citasMinimas
            it[porcentajeDescuento] = java.math.BigDecimal.valueOf(dto.porcentajeDescuento)
            it[descripcion] = dto.descripcion
        }
        if (updatedRows > 0) findById(id) else null
    }

    override fun deleteById(id: String): Boolean = transaction {
        val intId = id.toIntOrNull() ?: return@transaction false
        NivelesFidelidad.deleteWhere { NivelesFidelidad.id eq intId } > 0
    }
} 