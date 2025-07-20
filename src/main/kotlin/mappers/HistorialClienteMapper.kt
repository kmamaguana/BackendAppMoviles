package com.example.mappers

import com.example.db.HistorialCliente
import com.example.dto.HistorialClienteDTO
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toHistorialClienteDTO(): HistorialClienteDTO = HistorialClienteDTO(
    id = this[HistorialCliente.id].value.toString(),
    clienteId = this[HistorialCliente.clienteId].value.toString(),
    accion = this[HistorialCliente.accion],
    descripcion = this[HistorialCliente.descripcion],
    fecha = this[HistorialCliente.fecha].toString()
) 