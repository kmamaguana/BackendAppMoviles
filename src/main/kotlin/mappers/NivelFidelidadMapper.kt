package com.example.mappers

import com.example.db.NivelesFidelidad
import com.example.dto.NivelFidelidadDTO
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toNivelFidelidadDTO(): NivelFidelidadDTO = NivelFidelidadDTO(
    id = this[NivelesFidelidad.id].value.toString(),
    citasMinimas = this[NivelesFidelidad.citasMinimas],
    porcentajeDescuento = this[NivelesFidelidad.porcentajeDescuento].toDouble(),
    descripcion = this[NivelesFidelidad.descripcion]
) 