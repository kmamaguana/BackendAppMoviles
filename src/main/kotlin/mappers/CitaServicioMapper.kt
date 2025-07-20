package com.example.mappers

import com.example.db.CitaServicios
import com.example.dto.CitaServicioDTO
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toCitaServicioDTO(): CitaServicioDTO = CitaServicioDTO(
    id = this[CitaServicios.id].value.toString(),
    citaId = this[CitaServicios.citaId].value.toString(),
    servicioId = this[CitaServicios.servicioId].value.toString(),
    precioServicio = this[CitaServicios.precioServicio].toDouble()
) 