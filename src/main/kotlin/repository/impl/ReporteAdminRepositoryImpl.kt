package com.example.repository.impl

import com.example.db.ReportesAdmin
import com.example.domain.repository.ReporteAdminRepository
import com.example.dto.ReporteAdminDTO
import com.example.mappers.toReporteAdminDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ReporteAdminRepositoryImpl : ReporteAdminRepository {
    override fun findAll(): List<ReporteAdminDTO> = transaction {
        ReportesAdmin.selectAll().map { it.toReporteAdminDTO() }
    }

    override fun findById(id: String): ReporteAdminDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        ReportesAdmin.select { ReportesAdmin.id eq intId }
            .map { it.toReporteAdminDTO() }
            .singleOrNull()
    }

    override fun save(dto: ReporteAdminDTO): ReporteAdminDTO = transaction {
        val id = ReportesAdmin.insertAndGetId {
            it[fecha] = java.time.LocalDateTime.parse(dto.fecha)
            it[tipo] = dto.tipo
            it[contenido] = dto.contenido
            it[generadoPor] = dto.generadoPor.toInt()
        }
        findById(id.value.toString())!!
    }

    override fun update(id: String, dto: ReporteAdminDTO): ReporteAdminDTO? = transaction {
        val intId = id.toIntOrNull() ?: return@transaction null
        val updatedRows = ReportesAdmin.update({ ReportesAdmin.id eq intId }) {
            it[fecha] = java.time.LocalDateTime.parse(dto.fecha)
            it[tipo] = dto.tipo
            it[contenido] = dto.contenido
            it[generadoPor] = dto.generadoPor.toInt()
        }
        if (updatedRows > 0) findById(id) else null
    }

    override fun deleteById(id: String): Boolean = transaction {
        val intId = id.toIntOrNull() ?: return@transaction false
        ReportesAdmin.deleteWhere { ReportesAdmin.id eq intId } > 0
    }
}