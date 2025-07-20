package com.example.mappers

import com.example.db.Categorias
import com.example.dto.CategoriaDTO
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toCategoriaDTO(): CategoriaDTO = CategoriaDTO(
    id = this[Categorias.id].value.toString(),
    nombre = this[Categorias.nombre],
    descripcion = this[Categorias.descripcion]
) 