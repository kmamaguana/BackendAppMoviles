package com.example.mappers

import com.example.db.Carrito
import com.example.dto.CarritoDTO
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toCarritoDTO(): CarritoDTO = CarritoDTO(
    id = this[Carrito.id].value.toString(),
    clienteId = this[Carrito.clienteId].value.toString(),
    productoId = this[Carrito.productoId].value.toString(),
    cantidad = this[Carrito.cantidad]
) 