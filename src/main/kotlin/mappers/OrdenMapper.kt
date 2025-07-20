package com.example.mappers

import com.example.db.Ordenes
import com.example.dto.OrdenDTO
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toOrdenDTO(): OrdenDTO = OrdenDTO(
    id = this[Ordenes.id].value.toString(),
    clienteId = this[Ordenes.clienteId].value.toString(),
    fecha = this[Ordenes.fecha].toString(),
    total = this[Ordenes.total].toDouble(),
    estado = this[Ordenes.estado]
) 