package com.example.mappers

import com.example.db.OrdenDetalles
import com.example.dto.OrdenDetalleDTO
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toOrdenDetalleDTO(): OrdenDetalleDTO = OrdenDetalleDTO(
    id = this[OrdenDetalles.id].value.toString(),
    ordenId = this[OrdenDetalles.ordenId].value.toString(),
    productoId = this[OrdenDetalles.productoId].value.toString(),
    cantidad = this[OrdenDetalles.cantidad],
    precioUnitario = this[OrdenDetalles.precioUnitario].toDouble()
) 