package com.example.mappers

import com.example.db.Productos
import com.example.dto.ProductoDTO
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toProductoDTO(): ProductoDTO = ProductoDTO(
    id = this[Productos.id].value.toString(),
    nombre = this[Productos.nombre],
    descripcion = this[Productos.descripcion],
    precio = this[Productos.precio].toDouble(),
    stock = this[Productos.stock],
    imagenUrl = this[Productos.imagenUrl],
    categoriaId = this[Productos.categoriaId].value.toString()
) 