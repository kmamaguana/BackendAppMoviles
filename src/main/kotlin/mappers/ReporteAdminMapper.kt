package com.example.mappers

import com.example.db.ReportesAdmin
import com.example.dto.ReporteAdminDTO
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toReporteAdminDTO(): ReporteAdminDTO = ReporteAdminDTO(
    id = this[ReportesAdmin.id].value.toString(),
    fecha = this[ReportesAdmin.fecha].toString(),
    tipo = this[ReportesAdmin.tipo],
    contenido = this[ReportesAdmin.contenido],
    generadoPor = this[ReportesAdmin.generadoPor].value.toString()
) 