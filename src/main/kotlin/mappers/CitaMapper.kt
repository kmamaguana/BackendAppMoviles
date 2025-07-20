package com.example.mappers

import com.example.db.Citas
import com.example.dto.CitaDTO
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toCitaDTO(): CitaDTO = CitaDTO(
    id = this[Citas.id].value.toString(),
    mascotaId = this[Citas.mascotaId].value.toString(),
    fecha = this[Citas.fecha].toString(),
    estado = this[Citas.estado],
    notas = this[Citas.notas],
    creadoEn = this[Citas.creadoEn].toString()
) 