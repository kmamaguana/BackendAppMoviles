package com.example.mappers

import com.example.db.Clientes
import com.example.dto.ClienteDTO
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toClienteDTO(nombreUsuario: String): ClienteDTO = ClienteDTO(
    id = this[Clientes.id].value.toString(),
    usuarioId = this[Clientes.usuarioId].value.toString(),
    nombre = nombreUsuario,
    telefono = this[Clientes.telefono],
    direccion = this[Clientes.direccion]
) 