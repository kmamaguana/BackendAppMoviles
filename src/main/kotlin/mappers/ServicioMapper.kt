package com.example.mappers

import com.example.db.Servicios
import com.example.dto.ServicioDTO
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toServicioDTO(): ServicioDTO = ServicioDTO(
    id = this[Servicios.id].value.toString(),
    nombre = this[Servicios.nombre],
    tipo = this[Servicios.tipo],
    descripcion = this[Servicios.descripcion],
    precio = this[Servicios.precio].toDouble()
) 