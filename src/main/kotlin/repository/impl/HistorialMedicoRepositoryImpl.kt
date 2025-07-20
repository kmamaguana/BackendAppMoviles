package com.example.repository.impl

import com.example.db.HistorialMedico
import com.example.domain.repository.HistorialMedicoRepository
import com.example.dto.HistorialMedicoDTO
import com.example.dto.HistorialMedicoCreateUpdateDTO
import com.example.mappers.toHistorialMedicoDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class HistorialMedicoRepositoryImpl : HistorialMedicoRepository {
    override fun findAll(): List<HistorialMedicoDTO> = transaction {
        HistorialMedico.selectAll().map { it.toHistorialMedicoDTO() }
    }

    override fun findById(id: String): HistorialMedicoDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        HistorialMedico.select { HistorialMedico.id eq intId }
            .map { it.toHistorialMedicoDTO() }
            .singleOrNull()
    }

    override fun save(dto: HistorialMedicoCreateUpdateDTO): HistorialMedicoDTO = transaction {
        val id = HistorialMedico.insertAndGetId {
            it[mascotaId] = dto.mascotaId.toInt()
            it[fecha] = java.time.LocalDateTime.parse(dto.fecha)
            it[tipo] = dto.tipo
            it[descripcion] = dto.descripcion
            it[veterinario] = dto.veterinario
        }
        findById(id.value.toString())!!
    }

    override fun update(id: String, dto: HistorialMedicoCreateUpdateDTO): HistorialMedicoDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        val updatedRows = HistorialMedico.update({ HistorialMedico.id eq intId }) {
            it[mascotaId] = dto.mascotaId.toInt()
            it[fecha] = java.time.LocalDateTime.parse(dto.fecha)
            it[tipo] = dto.tipo
            it[descripcion] = dto.descripcion
            it[veterinario] = dto.veterinario
        }
        if (updatedRows > 0) findById(id) else null
    }

    override fun deleteById(id: String): Boolean = transaction {
        val intId = id.toIntOrNull() ?: return@transaction false
        HistorialMedico.deleteWhere { HistorialMedico.id eq intId } > 0
    }
} 