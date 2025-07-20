package com.example.mappers

import com.example.db.HistorialMedico
import com.example.dto.HistorialMedicoDTO
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toHistorialMedicoDTO(): HistorialMedicoDTO = HistorialMedicoDTO(
    id = this[HistorialMedico.id].value.toString(),
    mascotaId = this[HistorialMedico.mascotaId].value.toString(),
    fecha = this[HistorialMedico.fecha].toString(),
    tipo = this[HistorialMedico.tipo],
    descripcion = this[HistorialMedico.descripcion],
    veterinario = this[HistorialMedico.veterinario]
) 